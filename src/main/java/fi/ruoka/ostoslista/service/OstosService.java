package fi.ruoka.ostoslista.service;

import fi.ruoka.ostoslista.dto.OstosDto;

public interface OstosService {
    boolean deleteOstos(Long id);

    boolean addOstos(Long id, OstosDto dto);
}
