package fi.ruoka.ostoslista.business;

import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.OstosDto;
import fi.ruoka.ostoslista.dto.OstosListaDto;
import fi.ruoka.ostoslista.entity.OstosEntity;
import fi.ruoka.ostoslista.entity.OstosListaEntity;
import fi.ruoka.ostoslista.enums.Tuotteet;
import fi.ruoka.ostoslista.repository.OstosListaRepository;
import fi.ruoka.ostoslista.repository.OstosRepository;

@Service
public class OstosListaBusinessImpl implements OstosListaBusiness {

    @Autowired
    private OstosListaRepository repository;

    @Autowired
    private OstosRepository ostosRepository;

    private static final Logger logger = LoggerFactory.getLogger(OstosListaBusinessImpl.class);

    @Override
    public Optional<OstosListaEntity> getOstosListaById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<OstosListaEntity> getAllOstosLista() {
        return repository.findAll();
    }

    @Override
    public Optional<OstosListaEntity> createOstosLista(OstosListaDto dto) {
        try {
            OstosListaEntity ostosLista = new OstosListaEntity();
            ostosLista = saveOstosLista(ostosLista, dto);
            logger.info("OstosListaEntity with id {} created successfully.", ostosLista.getId());
            return Optional.of(ostosLista);
        } catch (Exception e) {
            logger.error(ErrorMessages.OL_SAVE_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<OstosListaEntity> updateOstosLista(Long id, OstosListaDto dto) {
        try {
            Optional<OstosListaEntity> optOstosLista = repository.findById(id);
            if (optOstosLista.isPresent()) {
                OstosListaEntity ostosListaEntity = optOstosLista.get();
                Iterator<OstosEntity> iterator = ostosListaEntity.getOstokset().iterator();
                while (iterator.hasNext()) {
                    OstosEntity ostos = iterator.next();
                    if (dto.getOstokset().stream().noneMatch(o -> o.getId().equals(ostos.getId()))) {
                        iterator.remove();
                        ostosRepository.delete(ostos);
                    }
                }

                dto.getOstokset().forEach(ostosDto -> {
                    if (ostosDto.getId() != null) {
                        OstosEntity ostos = ostosListaEntity.getOstokset().stream()
                                .filter(o -> o.getId().equals(ostosDto.getId()))
                                .findFirst().get();
                        ostos.setOstosLista(ostosListaEntity);
                        ostos.setMaara(ostosDto.getMaara());
                        ostos.setTuote(ostosDto.getTuote());
                        ostos.setYksikko(ostosDto.getYksikko());
                        ostos.setOstettu(ostosDto.getOstettu());
                        Tuotteet matchingTuote = Arrays.stream(Tuotteet.values())
                                .filter(t -> t.getTuote().contains(ostosDto.getTuote()))
                                .findFirst()
                                .orElse(null);
                        ostos.setOsastoId(matchingTuote != null ? matchingTuote.getOsastoId() : 0);
                    } else {
                        OstosEntity ostos = new OstosEntity();
                        ostos.setMaara(ostosDto.getMaara());
                        ostos.setTuote(ostosDto.getTuote());
                        ostos.setYksikko(ostosDto.getYksikko());
                        ostos.setOstettu(ostosDto.getOstettu());
                        ostos.setOstosLista(ostosListaEntity);
                        Tuotteet matchingTuote = Arrays.stream(Tuotteet.values())
                                .filter(t -> t.getTuote().contains(ostosDto.getTuote()))
                                .findFirst()
                                .orElse(null);
                        ostos.setOsastoId(matchingTuote != null ? matchingTuote.getOsastoId() : 0);
                        ostosListaEntity.getOstokset().add(ostos);
                    }
                });
                ostosListaEntity.setNimi(dto.getNimi());
                repository.save(ostosListaEntity);
                return Optional.of(ostosListaEntity);
            }
            return Optional.empty();

        } catch (Exception e) {
            logger.error(ErrorMessages.OL_UPDATE_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Boolean deleteOstosLista(Long id) {
        Optional<OstosListaEntity> optOstosLista = repository.findById(id);
        if (optOstosLista.isPresent()) {
            repository.delete(optOstosLista.get());
            return true;
        }
        return false;
    }

    private OstosListaEntity saveOstosLista(OstosListaEntity ostosLista, OstosListaDto dto) {
        ostosLista.setPaiva(Instant.now());
        ostosLista.setNimi(dto.getNimi());
        List<OstosEntity> ostokset = dto.getOstokset().stream()
                .map(this::ostosToEntity)
                .collect(Collectors.toList());
        ostosLista.setOstokset(ostokset);
        ostokset.forEach(ostos -> {
            ostos.setOstosLista(ostosLista);
        });
        repository.save(ostosLista);
        return ostosLista;
    }

    private OstosEntity ostosToEntity(OstosDto ostosDto) {
        OstosEntity entity = new OstosEntity();
        entity.setMaara(ostosDto.getMaara());
        entity.setTuote(ostosDto.getTuote());
        entity.setYksikko(ostosDto.getYksikko());
        entity.setOstettu(ostosDto.getOstettu());
        Tuotteet matchingTuote = Arrays.stream(Tuotteet.values())
                .filter(t -> t.getTuote().contains(ostosDto.getTuote()))
                .findFirst()
                .orElse(null);
        entity.setOsastoId(matchingTuote != null ? matchingTuote.getOsastoId() : 0);
        return entity;
    }
}
