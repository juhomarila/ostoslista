package fi.ruoka.ostoslista.business;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.dto.RuokaAineDto;
import fi.ruoka.ostoslista.entity.ReseptiEntity;
import fi.ruoka.ostoslista.entity.RuokaAineEntity;
import fi.ruoka.ostoslista.repository.ReseptiRepository;

@Service
public class ReseptiBusinessImpl implements ReseptiBusiness {

    @Autowired
    private ReseptiRepository reseptiRepository;

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
    public Optional<ReseptiEntity> updateResepti(ReseptiDto dto) {
        try {
            Optional<ReseptiEntity> resepti = reseptiRepository.findById(dto.getId());
            if (resepti.isPresent()) {
                resepti.get().getRuokaAineet().clear();
                resepti.get().getRuokaAineet().addAll(dto.getRuokaAineet().stream()
                        .map(this::ruokaAineetToEntity)
                        .collect(Collectors.toList()));
                resepti.get().setOhje(dto.getOhje());
                resepti.get().setNimi(dto.getNimi());
                reseptiRepository.save(resepti.get());
                return resepti;
            }
            return Optional.empty();
        } catch (Exception e) {
            logger.error(ErrorMessages.RESEPTI_UPDATE_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<ReseptiEntity> getAllResepti() {
        return reseptiRepository.findAll();
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
        ruokaAineet.forEach(ruokaAine -> {
            ruokaAine.setResepti(resepti);
        });
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
