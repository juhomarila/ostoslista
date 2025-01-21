package fi.ruoka.ostoslista.service;

import fi.ruoka.ostoslista.dto.OstosDto;

public interface OstosService {
    ValidatedServiceResult<Boolean> deleteOstos(Long id);

    ValidatedServiceResult<OstosDto> addOstos(Long id, OstosDto dto);

    ValidatedServiceResult<OstosDto> getOstosById(Long id);

    ValidatedServiceResult<OstosDto> updateOstosById(Long id, OstosDto dto);
}
