package fi.ruoka.ostoslista.business;

import java.util.List;
import java.util.Optional;

import fi.ruoka.ostoslista.dto.TuoteDto;
import fi.ruoka.ostoslista.elasticsearch.TuoteDocument;
import fi.ruoka.ostoslista.entity.TuoteEntity;

public interface TuoteBusiness {
    Optional<TuoteEntity> addTuote(TuoteDto dto);

    List<TuoteEntity> getAllTuotteet();

    Optional<TuoteEntity> getTuoteById(Long id);

    Boolean deleteTuote(Long id);

    Optional<TuoteEntity> updateTuote(Long id, TuoteDto dto);

    List<String> getYksikot();

    List<TuoteEntity> getActiveTuotteet();

    List<TuoteDocument> getTuoteByTuote(String tuote);
}
