package fi.ruoka.ostoslista.business;

import java.util.List;
import java.util.Optional;

import fi.ruoka.ostoslista.dto.OstosListaDto;
import fi.ruoka.ostoslista.entity.OstosListaEntity;

public interface OstosListaBusiness {
    Optional<OstosListaEntity> getOstosListaById(Long id);

    Optional<OstosListaEntity> createOstosLista(OstosListaDto dto);

    Optional<OstosListaEntity> updateOstosLista(Long id, OstosListaDto dto);

    boolean deleteOstosLista(Long id);

    List<OstosListaEntity> getAllOstosLista();
}
