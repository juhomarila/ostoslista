package fi.ruoka.ostoslista.business;

import java.util.Optional;

import fi.ruoka.ostoslista.dto.OstosDto;
import fi.ruoka.ostoslista.entity.OstosEntity;

public interface OstosBusiness {
    Boolean deleteOstos(Long id);

    Optional<OstosEntity> addOstos(Long id, OstosDto dto);

    Optional<OstosEntity> getOstosById(Long id);

    Optional<OstosEntity> updateOstosById(Long id, OstosDto dto);
}
