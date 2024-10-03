package fi.ruoka.ostoslista.business;

import fi.ruoka.ostoslista.dto.OstosDto;

public interface OstosBusiness {
    boolean deleteOstos(Long id);

    boolean addOstos(Long id, OstosDto dto);
}
