package fi.ruoka.ostoslista.service;

import java.util.List;

import fi.ruoka.ostoslista.dto.OstosListaDto;
import fi.ruoka.ostoslista.dto.ReseptiDto;

public interface OstosListaService {
    ValidateServiceResult<OstosListaDto> createOstosLista(OstosListaDto dto);

    ValidateServiceResult<OstosListaDto> getOstosListaById(Long id);

    ValidateServiceResult<OstosListaDto> updateOstosLista(Long id, OstosListaDto dto);

    ValidateServiceResult<Boolean> deleteOstosLista(Long id);

    ValidateServiceResult<List<OstosListaDto>> getAllOstosLista();

    ValidateServiceResult<OstosListaDto> reseptiToOstosLista(ReseptiDto dto);

    ValidateServiceResult<OstosListaDto> reseptiToExistingOstosLista(ReseptiDto dto, Long id);
}
