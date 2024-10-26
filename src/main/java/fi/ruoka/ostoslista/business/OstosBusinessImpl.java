package fi.ruoka.ostoslista.business;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.OstosDto;
import fi.ruoka.ostoslista.entity.OstosEntity;
import fi.ruoka.ostoslista.entity.OstosListaEntity;
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
                ostosRepository.save(ostos);
                return Optional.of(ostos);
            }
        } catch (Exception e) {
            logger.error(ErrorMessages.OSTOS_SAVE_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<OstosEntity> getOstosById(Long id) {
        return ostosRepository.findById(id);
    }
}
