package fi.ruoka.ostoslista.service;

import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.entity.ReseptiEntity;

import java.util.List;

public interface ReseptiService {
    boolean createResepti(ReseptiDto dto);

    List<ReseptiDto> getAllResepti();

    ReseptiDto reseptiToDto(ReseptiEntity resepti);

    ReseptiDto getReseptiById(Long id);

    boolean deleteResepti(Long id);
}
