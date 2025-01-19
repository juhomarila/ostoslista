package fi.ruoka.ostoslista.business;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.TuoteDto;
import fi.ruoka.ostoslista.entity.TuoteEntity;
import fi.ruoka.ostoslista.repository.TuoteRepository;

@Service
public class TuoteBusinessImpl implements TuoteBusiness {

    @Autowired
    private TuoteRepository tuoteRepository;

    private static final Logger logger = LoggerFactory.getLogger(TuoteBusinessImpl.class);

    @Override
    public Optional<TuoteEntity> addTuote(TuoteDto dto) {
        try {
            TuoteEntity tuote = new TuoteEntity();
            tuote.setTuote(dto.getTuote());
            if (dto.getOsasto() != null) {
                tuote.setOsasto(dto.getOsasto());
            }
            if (dto.getHinta() != 0.0) {
                tuote.setHinta(dto.getHinta());
            }  
            tuote = tuoteRepository.save(tuote);
            return Optional.of(tuote);
        } catch (Exception e) {
            logger.error(ErrorMessages.TUOTE_SAVE_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<TuoteEntity> getAllTuotteet() {
        return tuoteRepository.findAll();
    }

    @Override
    public Optional<TuoteEntity> getTuoteById(Long id) {
        return tuoteRepository.findById(id);
    }

    @Override
    public Boolean deleteTuote(Long id) {
        try {
            tuoteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(ErrorMessages.TUOTE_DELETION_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<TuoteEntity> updateTuote(Long id, TuoteDto dto) {
        try {
            Optional<TuoteEntity> tuote = tuoteRepository.findById(id);
            if (tuote.isPresent()) {
                TuoteEntity tuoteEntity = tuote.get();
                tuoteEntity.setTuote(dto.getTuote());
                if (dto.getOsasto() != null) {
                    tuoteEntity.setOsasto(dto.getOsasto());
                }
                if (dto.getHinta() != 0.0) {
                    tuoteEntity.setHinta(dto.getHinta());
                }
                tuoteEntity = tuoteRepository.save(tuoteEntity);
                return Optional.of(tuoteEntity);
            }
            return Optional.empty();
        } catch (Exception e) {
            logger.error(ErrorMessages.TUOTE_UPDATE_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
