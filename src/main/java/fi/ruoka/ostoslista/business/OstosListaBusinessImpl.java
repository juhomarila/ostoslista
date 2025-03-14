package fi.ruoka.ostoslista.business;

import java.time.Instant;
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
import fi.ruoka.ostoslista.entity.ReseptiEntity;
import fi.ruoka.ostoslista.entity.ReseptiOstoEntity;
import fi.ruoka.ostoslista.entity.TuoteEntity;
import fi.ruoka.ostoslista.entity.TuoteOstoEntity;
import fi.ruoka.ostoslista.repository.OstosListaRepository;
import fi.ruoka.ostoslista.repository.OstosRepository;
import fi.ruoka.ostoslista.repository.ReseptiOstoRepository;
import fi.ruoka.ostoslista.repository.ReseptiRepository;
import fi.ruoka.ostoslista.repository.TuoteOstoRepository;
import fi.ruoka.ostoslista.repository.TuoteRepository;

@Service
public class OstosListaBusinessImpl implements OstosListaBusiness {

    @Autowired
    private OstosListaRepository repository;

    @Autowired
    private OstosRepository ostosRepository;

    @Autowired
    private TuoteRepository tuoteRepository;

    @Autowired
    private TuoteOstoRepository tuoteOstoRepository;

    @Autowired
    private ReseptiRepository reseptiRepository;

    @Autowired
    private ReseptiOstoRepository reseptiOstoRepository;

    private static final Logger logger = LoggerFactory.getLogger(OstosListaBusinessImpl.class);

    @Override
    public Optional<OstosListaEntity> getOstosListaById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<OstosListaEntity> getAllOstosLista() {
        return repository.findByValmisFalse();
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
                    if (dto.getOstokset().stream()
                            .noneMatch(o -> o.getId() != null && o.getId().equals(ostos.getId()))) {
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
                        TuoteEntity matchingTuote = tuoteRepository.findByTuote(ostosDto.getTuote()).get();
                        ostos.setOsastoId(matchingTuote.getOsasto());
                    } else if (!ostosListaEntity.getOstokset().isEmpty()
                            && ostosListaEntity.getOstokset().stream()
                                    .anyMatch(o -> o.getTuote().equals(ostosDto.getTuote())
                                    && o.getYksikko().equals(ostosDto.getYksikko()))) {
                        OstosEntity ostos = ostosListaEntity.getOstokset().stream()
                                .filter(o -> o.getTuote().equals(ostosDto.getTuote())
                                && o.getYksikko().equals(ostosDto.getYksikko()))
                                .findFirst().get();
                        ostos.setMaara(ostos.getMaara() + ostosDto.getMaara());
                    } else {
                        OstosEntity ostos = new OstosEntity();
                        ostos.setMaara(ostosDto.getMaara());
                        ostos.setTuote(ostosDto.getTuote());
                        ostos.setYksikko(ostosDto.getYksikko());
                        ostos.setOstettu(ostosDto.getOstettu());
                        ostos.setOstosLista(ostosListaEntity);
                        TuoteEntity matchingTuote = tuoteRepository.findByTuote(ostosDto.getTuote()).get();
                        ostos.setOsastoId(matchingTuote.getOsasto());
                        ostosListaEntity.getOstokset().add(ostos);
                    }
                });
                ostosListaEntity.setNimi(dto.getNimi());
                ostosListaEntity.setValmis(dto.isValmis());
                ostosListaEntity.setReseptiId(dto.getReseptiId());
                ostosListaEntity.setModified(Instant.now()); 
                ostosListaEntity.setVersion(ostosListaEntity.getVersion() + 1);
                repository.save(ostosListaEntity);
                return Optional.of(ostosListaEntity);
            }
            logger.warn(ErrorMessages.OL_UPDATE_ERROR + "OstosListaEntity not found.");
            return Optional.empty();

        } catch (Exception e) {
            logger.error(ErrorMessages.OL_UPDATE_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> setOstosListaValmis(Long id) {
        Optional<OstosListaEntity> optOstosLista = repository.findById(id);
        if (optOstosLista.isPresent()) {
            OstosListaEntity ostosLista = optOstosLista.get();
            ostosLista.setValmis(true);
            ostosLista.setModified(Instant.now());
            ostosLista.setVersion(ostosLista.getVersion() + 1);
            ostosLista.getOstokset().forEach(ostos -> {
                if (ostos.getOstettu()) {
                    Optional<TuoteEntity> tuote = tuoteRepository.findByTuote(ostos.getTuote());
                    if (tuote.isPresent()) {
                        TuoteEntity tuoteEntity = tuote.get();
                        tuoteEntity.setOstoKerrat(tuoteEntity.getOstoKerrat() + 1);
                        tuoteEntity.setKplOstettu(tuoteEntity.getKplOstettu() != null ? tuoteEntity.getKplOstettu() + ostos.getMaara().intValue() : ostos.getMaara().intValue());
                        tuoteRepository.save(tuoteEntity);
                        TuoteOstoEntity tuoteOstoEntity = new TuoteOstoEntity();
                        tuoteOstoEntity.setOstoAika(Instant.now());
                        tuoteOstoEntity.setTuote(tuoteEntity);
                        tuoteOstoRepository.save(tuoteOstoEntity);
                    }
                }
            });
            if (ostosLista.getReseptiId() != null) {
                ostosLista.getReseptiId().forEach(reseptiId -> {
                    Optional<ReseptiEntity> optEntity = reseptiRepository.findById(reseptiId);
                    if (optEntity.isPresent()) {
                        ReseptiEntity reseptiEntity = optEntity.get();
                        reseptiEntity.setOstoKerrat(reseptiEntity.getOstoKerrat() + 1);
                        reseptiRepository.save(reseptiEntity);
                        ReseptiOstoEntity reseptiOstoEntity = new ReseptiOstoEntity();
                        reseptiOstoEntity.setOstoAika(Instant.now());
                        reseptiOstoEntity.setResepti(reseptiEntity);
                        reseptiOstoRepository.save(reseptiOstoEntity);
                    }
                });
            }
            repository.save(ostosLista);
            return Optional.of(true);
        }
        return Optional.empty();
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
        ostosLista.setCreated(Instant.now());
        ostosLista.setModified(Instant.now());
        ostosLista.setVersion(1);
        if (dto.getReseptiId() != null) {
            ostosLista.setReseptiId(dto.getReseptiId());
        }
        if (dto.getOstokset() != null) {
            List<OstosEntity> ostokset = dto.getOstokset().stream()
                    .map(this::ostosToEntity)
                    .collect(Collectors.toList());
            ostosLista.setOstokset(ostokset);
            ostokset.forEach(ostos -> {
                ostos.setOstosLista(ostosLista);
            });
        }
        repository.save(ostosLista);
        return ostosLista;
    }

    private OstosEntity ostosToEntity(OstosDto ostosDto) {
        OstosEntity entity = new OstosEntity();
        entity.setMaara(ostosDto.getMaara());
        entity.setTuote(ostosDto.getTuote());
        entity.setYksikko(ostosDto.getYksikko());
        entity.setOstettu(ostosDto.getOstettu());
        /*
         * and also do a check if frontend sends ostoslista or resepti that is in different state than
         * in the database, include version and timestamp into the different entities (atleas ostoslista and resepti) 
         * and add a check for version change. t.eg if frontend has version two and database 3.
         * This adds possibility to simultaneos updating of ostoslista and resepti
         */
        TuoteEntity matchingTuote = tuoteRepository.findByTuote(ostosDto.getTuote()).get();
        entity.setOsastoId(matchingTuote.getOsasto());
        return entity;
    }
}
