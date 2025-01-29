package fi.ruoka.ostoslista.service;

import java.util.List;

import fi.ruoka.ostoslista.dto.OstosListaDto;
import fi.ruoka.ostoslista.dto.ReseptiDto;

public interface OstosListaService {
    ValidatedServiceResult<OstosListaDto> createOstosLista(OstosListaDto dto);

    ValidatedServiceResult<OstosListaDto> getOstosListaById(Long id);

    ValidatedServiceResult<OstosListaDto> updateOstosLista(Long id, OstosListaDto dto);

    ValidatedServiceResult<Boolean> setOstosListaValmis(Long id);

    ValidatedServiceResult<Boolean> deleteOstosLista(Long id);

    ValidatedServiceResult<List<OstosListaDto>> getAllOstosLista();

    ValidatedServiceResult<OstosListaDto> reseptiToOstosLista(ReseptiDto dto);

    ValidatedServiceResult<OstosListaDto> reseptiToExistingOstosLista(ReseptiDto dto, Long id);
}
