package fi.ruoka.ostoslista.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.business.ReseptiBusiness;
import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.dto.RuokaAineDto;
import fi.ruoka.ostoslista.entity.ReseptiEntity;
import fi.ruoka.ostoslista.entity.RuokaAineEntity;

@Service
public class ReseptiServiceImpl implements ReseptiService {

    @Autowired
    private ReseptiBusiness business;

    @Override
    public boolean createResepti(ReseptiDto dto) {
        Optional<ReseptiEntity> optResepti = business.createResepti(dto);
        if (optResepti.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public List<ReseptiDto> getAllResepti() {
        List<ReseptiDto> reseptiDtos = business.getAllResepti().stream().map(res -> reseptiToDto(res))
                .collect(Collectors.toList());
        return reseptiDtos;
    }

    @Override
    public ReseptiDto getReseptiById(Long id) {
        Optional<ReseptiEntity> entityOptional = business.getReseptiById(id);
        if (entityOptional.isPresent()) {
            ReseptiEntity entity = entityOptional.get();
            return reseptiToDto(entity);
        }
        return null;
    }

    @Override
    public boolean deleteResepti(Long id) {
        return business.deleteResepti(id);
    }

    @Override
    public ReseptiDto reseptiToDto(ReseptiEntity resepti) {
        ReseptiDto reseptiDto = new ReseptiDto();

        reseptiDto.setId(resepti.getId());
        reseptiDto.setOhje(resepti.getOhje());
        reseptiDto.setNimi(resepti.getNimi());
        reseptiDto.setAdded(resepti.getAdded());
        reseptiDto.setRuokaAineet(ruokaAineetToDto(resepti.getRuokaAineet()));

        return reseptiDto;
    }

    private List<RuokaAineDto> ruokaAineetToDto(List<RuokaAineEntity> ruokaAineet) {
        List<RuokaAineDto> ruokaAineetDtosList = new ArrayList<>();
        ruokaAineet.forEach(ruokaAine -> {
            RuokaAineDto ruokaAineDto = new RuokaAineDto();
            ruokaAineDto.setId(ruokaAine.getId());
            ruokaAineDto.setMaara(ruokaAine.getMaara());
            ruokaAineDto.setRuokaAine(ruokaAine.getRuokaAine());
            ruokaAineDto.setYksikko(ruokaAine.getYksikko());
            ruokaAineetDtosList.add(ruokaAineDto);
        });
        return ruokaAineetDtosList;
    }
}
