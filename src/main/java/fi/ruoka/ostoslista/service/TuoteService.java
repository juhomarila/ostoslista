package fi.ruoka.ostoslista.service;

import java.util.List;

import fi.ruoka.ostoslista.dto.TuoteDto;

public interface TuoteService {
    ValidateServiceResult<TuoteDto> addTuote(TuoteDto dto);

    ValidateServiceResult<List<TuoteDto>> getAllTuotteet();

    ValidateServiceResult<TuoteDto> getTuoteById(Long id);

    ValidateServiceResult<Boolean> deleteTuote(Long id);

    ValidateServiceResult<TuoteDto> updateTuote(Long id, TuoteDto dto);
    
}
