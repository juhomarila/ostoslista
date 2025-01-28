package fi.ruoka.ostoslista.business;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.OstosDto;
import fi.ruoka.ostoslista.entity.OstosEntity;
import fi.ruoka.ostoslista.entity.OstosListaEntity;
import fi.ruoka.ostoslista.enums.Tuotteet;
import fi.ruoka.ostoslista.repository.OstosListaRepository;
import fi.ruoka.ostoslista.repository.OstosRepository;

@Service
public class OstosBusinessImpl implements OstosBusiness {

    @Autowired
    private OstosRepository ostosRepository;

    @Autowired
    private OstosListaRepository ostosListaRepository;

    private static final Logger logger = LoggerFactory.getLogger(OstosBusinessImpl.class);

    @Override
    public Boolean deleteOstos(Long id) {
        try {
            if (ostosRepository.existsById(id)) {
                OstosEntity ostosEntity = ostosRepository.findById(id).get();
                Optional<OstosListaEntity> optOstosListaEntity = ostosListaRepository
                        .findById(ostosEntity.getOstosLista().getId());
                if (optOstosListaEntity.isPresent()) {
                    optOstosListaEntity.get().getOstokset().remove(ostosEntity);
                    ostosListaRepository.save(ostosEntity.getOstosLista());
                    logger.info("OstosEntity with id {} deleted successfully.", id);
                    return true;
                } else {
                    logger.warn("OstosListaEntity with id {} not found.", ostosEntity.getOstosLista().getId());
                    return false;
                }
            } else {
                logger.warn("OstosEntity with id {} not found.", id);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error deleting OstosEntity with id {}: {}", id, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Optional<OstosEntity> addOstos(Long id, OstosDto dto) {
        try {
            Optional<OstosListaEntity> optOstosLista = ostosListaRepository.findById(id);
            if (optOstosLista.isPresent()) {
                OstosListaEntity ostosListaEntity = optOstosLista.get();
                OstosEntity ostos = new OstosEntity();
                ostos.setMaara(dto.getMaara());
                ostos.setTuote(dto.getTuote());
                ostos.setYksikko(dto.getYksikko());
                ostos.setOstosLista(ostosListaEntity);
                ostos.setOstettu(dto.getOstettu());
                Tuotteet matchingTuote = Arrays.stream(Tuotteet.values())
                        .filter(t -> t.getTuote().contains(dto.getTuote()))
                        .findFirst()
                        .orElse(null);
                ostos.setOsastoId(matchingTuote != null ? matchingTuote.getOsastoId() : 0);
                ostosRepository.save(ostos);
                return Optional.of(ostos);
            }
        } catch (Exception e) {
            logger.error(ErrorMessages.OSTOS_SAVE_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return Optional.empty();
        }
        logger.warn("OstosListaEntity with id {} not found.", id);
        return Optional.empty();
    }

    @Override
    public Optional<OstosEntity> getOstosById(Long id) {
        return ostosRepository.findById(id);
    }

    @Override
    public Optional<OstosEntity> updateOstosById(Long id, OstosDto dto) {
        try {
            Optional<OstosEntity> optOstos = ostosRepository.findById(id);
            if (optOstos.isPresent()) {
                OstosEntity ostos = optOstos.get();
                ostos.setMaara(dto.getMaara());
                ostos.setTuote(dto.getTuote());
                ostos.setYksikko(dto.getYksikko());
                ostos.setOstettu(dto.getOstettu());
                Tuotteet matchingTuote = Arrays.stream(Tuotteet.values())
                        .filter(t -> t.getTuote().contains(dto.getTuote()))
                        .findFirst()
                        .orElse(null);
                ostos.setOsastoId(matchingTuote != null ? matchingTuote.getOsastoId() : 0);
                ostosRepository.save(ostos);
                return Optional.of(ostos);
            }
        } catch (Exception e) {
            logger.error(ErrorMessages.OSTOS_UPDATE_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return Optional.empty();
        }
        logger.warn("OstosEntity with id {} not found.", id);
        return Optional.empty();
    }
}
