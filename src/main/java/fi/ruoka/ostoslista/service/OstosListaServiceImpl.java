package fi.ruoka.ostoslista.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.business.OstosListaBusiness;
import fi.ruoka.ostoslista.dto.OstosDto;
import fi.ruoka.ostoslista.dto.OstosListaDto;
import fi.ruoka.ostoslista.entity.OstosEntity;
import fi.ruoka.ostoslista.entity.OstosListaEntity;

@Service
public class OstosListaServiceImpl implements OstosListaService {

    @Autowired
    private OstosListaBusiness business;

    @Override
    public boolean createOstosLista(OstosListaDto dto) {
        Optional<OstosListaEntity> optOstosLista = business.createOstosLista(dto);
        if (optOstosLista.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public List<OstosListaDto> getAllOstosLista() {
        List<OstosListaDto> ostosListaDtos = business.getAllOstosLista().stream()
                .map(ostosLista -> ostosListaToDto(ostosLista))
                .collect(Collectors.toList());
        return ostosListaDtos;
    }

    @Override
    public OstosListaDto getOstosListaById(Long id) {
        Optional<OstosListaEntity> entityOptional = business.getOstosListaById(id);
        if (entityOptional.isPresent()) {
            OstosListaEntity entity = entityOptional.get();
            return ostosListaToDto(entity);
        }
        return null;
    }

    @Override
    public boolean updateOstosLista(Long id, OstosListaDto dto) {
        Optional<OstosListaEntity> optOstosLista = business.updateOstosLista(id, dto);
        if (optOstosLista.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOstosLista(Long id) {
        return business.deleteOstosLista(id);
    }

    private OstosListaDto ostosListaToDto(OstosListaEntity entity) {
        OstosListaDto dto = new OstosListaDto();

        dto.setId(entity.getId());
        dto.setNimi(entity.getNimi());
        dto.setPaiva(entity.getPaiva());
        dto.setOstokset(ostosToDto(entity.getOstokset()));

        return dto;
    }

    private List<OstosDto> ostosToDto(List<OstosEntity> ostokset) {
        List<OstosDto> ostosDtosList = new ArrayList<>();
        ostokset.forEach(ostos -> {
            OstosDto ostosDto = new OstosDto();
            ostosDto.setId(ostos.getId());
            ostosDto.setMaara(ostos.getMaara());
            ostosDto.setTuote(ostos.getTuote());
            ostosDto.setYksikko(ostos.getYksikko());
            ostosDto.setOstosListaId(ostos.getOstosLista().getId());
            ostosDtosList.add(ostosDto);
        });
        return ostosDtosList;
    }

}
