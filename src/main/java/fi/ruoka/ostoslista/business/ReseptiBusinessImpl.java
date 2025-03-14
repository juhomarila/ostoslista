package fi.ruoka.ostoslista.business;

import java.time.Instant;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.dto.RuokaAineDto;
import fi.ruoka.ostoslista.entity.ReseptiEntity;
import fi.ruoka.ostoslista.entity.RuokaAineEntity;
import fi.ruoka.ostoslista.repository.ReseptiRepository;
import fi.ruoka.ostoslista.repository.RuokaAineRepository;

@Service
public class ReseptiBusinessImpl implements ReseptiBusiness {

    @Autowired
    private ReseptiRepository reseptiRepository;

    @Autowired
    private RuokaAineRepository ruokaAineRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReseptiBusinessImpl.class);

    @Override
    public Optional<ReseptiEntity> createResepti(ReseptiDto dto) {
        try {
            ReseptiEntity resepti = new ReseptiEntity();
            resepti = saveResepti(resepti, dto);
            return Optional.of(resepti);
        } catch (Exception e) {
            logger.error(ErrorMessages.RESEPTI_SAVE_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return Optional.empty();
        }

    }

    @Override
    public Optional<ReseptiEntity> updateResepti(Long id, ReseptiDto dto) throws ReseptiError {
        try {
            Optional<ReseptiEntity> resepti = reseptiRepository.findById(id);
            if (resepti.isPresent()) {
                ReseptiEntity reseptiEntity = resepti.get();
                Iterator<RuokaAineEntity> iterator = reseptiEntity.getRuokaAineet().iterator();
                while (iterator.hasNext()) {
                    RuokaAineEntity ruokaAine = iterator.next();
                    if (dto.getRuokaAineet().stream().noneMatch(ra -> ra.getId() != null && ra.getId().equals(ruokaAine.getId()))) {
                        iterator.remove();
                        ruokaAineRepository.delete(ruokaAine);
                    }
                }
                dto.getRuokaAineet().forEach(ruokaAineDto -> {
                    if (ruokaAineDto.getId() != null) {
                        RuokaAineEntity ruokaAine = reseptiEntity.getRuokaAineet().stream()
                                .filter(ra -> ra.getId().equals(ruokaAineDto.getId()))
                                .findFirst().get();
                        ruokaAine.setResepti(reseptiEntity);
                        ruokaAine.setMaara(ruokaAineDto.getMaara());
                        ruokaAine.setRuokaAine(ruokaAineDto.getRuokaAine());
                        ruokaAine.setYksikko(ruokaAineDto.getYksikko());
                    } else {
                        RuokaAineEntity ruokaAine = new RuokaAineEntity();
                        ruokaAine.setMaara(ruokaAineDto.getMaara());
                        ruokaAine.setRuokaAine(ruokaAineDto.getRuokaAine());
                        ruokaAine.setYksikko(ruokaAineDto.getYksikko());
                        ruokaAine.setResepti(reseptiEntity);
                        reseptiEntity.getRuokaAineet().add(ruokaAine);
                    }
                });
                reseptiEntity.setOhje(dto.getOhje());
                reseptiEntity.setNimi(dto.getNimi());
                reseptiEntity.setModified(Instant.now());
                reseptiEntity.setVersion(reseptiEntity.getVersion() + 1);
                reseptiEntity.setOstoKerrat(dto.getOstoKerrat());
                if (dto.getOriginalLink() != null) {
                    reseptiEntity.setOriginalLink(dto.getOriginalLink());
                }
                ReseptiEntity savedReseptiEntity = reseptiRepository.save(reseptiEntity);
                return Optional.of(savedReseptiEntity);
            }
            logger.warn(ErrorMessages.RESEPTI_UPDATE_ERROR + " Resepti not found");
            return Optional.empty();
        } catch (Exception e) {
            logger.error(ErrorMessages.RESEPTI_UPDATE_ERROR + e.getMessage(), e);
            e.printStackTrace();
            throw new ReseptiError(ErrorMessages.RESEPTI_UPDATE_ERROR);
        }
    }

    @Override
    public List<ReseptiEntity> getAllResepti() {
        List<ReseptiEntity> reseptit = reseptiRepository.findAll();
        Collections.sort(reseptit, (r1, r2) -> r2.getOstoKerrat().compareTo(r1.getOstoKerrat()));
        return reseptit;
    }

    @Override
    public Optional<ReseptiEntity> getReseptiById(Long id) {
        return reseptiRepository.findById(id);
    }

    @Override
    public Boolean deleteResepti(Long id) {
        reseptiRepository.deleteById(id);
        return true;
    }

    private ReseptiEntity saveResepti(ReseptiEntity resepti, ReseptiDto dto) {
        resepti.setOhje(dto.getOhje());
        List<RuokaAineEntity> ruokaAineet = dto.getRuokaAineet().stream()
                .map(this::ruokaAineetToEntity)
                .collect(Collectors.toList());
        resepti.setRuokaAineet(ruokaAineet);
        resepti.setAdded(Instant.now());
        resepti.setNimi(dto.getNimi());
        resepti.setCreated(Instant.now());
        resepti.setModified(Instant.now());
        resepti.setVersion(1);
        resepti.setOstoKerrat(dto.getOstoKerrat());
        ruokaAineet.forEach(ruokaAine -> {
            ruokaAine.setResepti(resepti);
        });
        if (dto.getOriginalLink() != null) {
            resepti.setOriginalLink(dto.getOriginalLink());
        }
        reseptiRepository.save(resepti);
        return resepti;
    }

    private RuokaAineEntity ruokaAineetToEntity(RuokaAineDto ruokaAineDto) {
        RuokaAineEntity entity = new RuokaAineEntity();
        entity.setMaara(ruokaAineDto.getMaara());
        entity.setRuokaAine(ruokaAineDto.getRuokaAine());
        entity.setYksikko(ruokaAineDto.getYksikko());
        return entity;
    }
}
