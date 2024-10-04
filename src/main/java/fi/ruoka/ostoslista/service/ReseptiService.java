package fi.ruoka.ostoslista.service;

import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.entity.ReseptiEntity;

import java.util.List;

public interface ReseptiService {
    ValidateServiceResult<ReseptiDto> createResepti(ReseptiDto dto);

    ValidateServiceResult<List<ReseptiDto>> getAllResepti();

    ReseptiDto reseptiToDto(ReseptiEntity resepti);

    ValidateServiceResult<ReseptiDto> getReseptiById(Long id);

    ValidateServiceResult<Boolean> deleteResepti(Long id);

    ValidateServiceResult<ReseptiDto> updateResepti(Long id, ReseptiDto dto);
}
