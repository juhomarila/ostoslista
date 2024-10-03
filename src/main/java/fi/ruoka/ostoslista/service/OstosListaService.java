package fi.ruoka.ostoslista.service;

import java.util.List;

import fi.ruoka.ostoslista.dto.OstosListaDto;

public interface OstosListaService {
    boolean createOstosLista(OstosListaDto dto);

    OstosListaDto getOstosListaById(Long id);

    boolean updateOstosLista(Long id, OstosListaDto dto);

    boolean deleteOstosLista(Long id);

    List<OstosListaDto> getAllOstosLista();
}
