package fi.ruoka.ostoslista.service;

import java.util.List;

import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.entity.ReseptiEntity;

public interface ReseptiService {
    ValidatedServiceResult<ReseptiDto> createResepti(ReseptiDto dto);

    ValidatedServiceResult<List<ReseptiDto>> getAllResepti();

    ReseptiDto reseptiToDto(ReseptiEntity resepti);

    ValidatedServiceResult<ReseptiDto> getReseptiById(Long id);

    ValidatedServiceResult<Boolean> deleteResepti(Long id);

    ValidatedServiceResult<ReseptiDto> updateResepti(Long id, ReseptiDto dto);
}
