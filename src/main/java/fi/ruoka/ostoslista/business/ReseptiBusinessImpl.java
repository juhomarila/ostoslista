package fi.ruoka.ostoslista.business;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.time.Instant;

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

    @Override
    public Optional<ReseptiEntity> createResepti(ReseptiDto dto) {
        ReseptiEntity resepti = new ReseptiEntity();
        resepti = saveResepti(resepti, dto);
        return Optional.of(resepti);
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
    public boolean deleteResepti(Long id) {
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
