package fi.ruoka.ostoslista.service;

import java.util.List;

import fi.ruoka.ostoslista.dto.TuoteDto;
import fi.ruoka.ostoslista.elasticsearch.TuoteDocument;

public interface TuoteService {
    ValidatedServiceResult<TuoteDto> addTuote(TuoteDto dto);

    ValidatedServiceResult<List<TuoteDto>> getAllTuotteet();

    ValidatedServiceResult<TuoteDto> getTuoteById(Long id);

    ValidatedServiceResult<Boolean> deleteTuote(Long id);

    ValidatedServiceResult<TuoteDto> updateTuote(Long id, TuoteDto dto);

    ValidatedServiceResult<List<String>> getYksikot();

    ValidatedServiceResult<List<TuoteDto>> getActiveTuotteet();   
    
    ValidatedServiceResult<List<TuoteDocument>> getTuoteByTuote(String tuote);
}
