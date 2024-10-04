package fi.ruoka.ostoslista.business;

import java.util.Optional;
import java.util.List;

import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.entity.ReseptiEntity;

public interface ReseptiBusiness {
    Optional<ReseptiEntity> createResepti(ReseptiDto dto);

    List<ReseptiEntity> getAllResepti();

    Optional<ReseptiEntity> getReseptiById(Long id);

    Boolean deleteResepti(Long id);

    Optional<ReseptiEntity> updateResepti(Long id, ReseptiDto dto);
}
