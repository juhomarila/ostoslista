package fi.ruoka.ostoslista.service;

import fi.ruoka.ostoslista.dto.OstosDto;

public interface OstosService {
    ValidateServiceResult<Boolean> deleteOstos(Long id);

    ValidateServiceResult<OstosDto> addOstos(Long id, OstosDto dto);

    ValidateServiceResult<OstosDto> getOstosById(Long id);
}
