package fi.ruoka.ostoslista.service;

import java.util.List;

import fi.ruoka.ostoslista.dto.OstosListaDto;

public interface OstosListaService {
    ValidateServiceResult<OstosListaDto> createOstosLista(OstosListaDto dto);

    ValidateServiceResult<OstosListaDto> getOstosListaById(Long id);

    ValidateServiceResult<OstosListaDto> updateOstosLista(OstosListaDto dto);

    ValidateServiceResult<Boolean> deleteOstosLista(Long id);

    ValidateServiceResult<List<OstosListaDto>> getAllOstosLista();
}
