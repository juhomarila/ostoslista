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
        ostosRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<OstosEntity> addOstos(Long id, OstosDto dto) {
        try {
            OstosEntity ostos = new OstosEntity();
            ostos.setMaara(dto.getMaara());
            ostos.setTuote(dto.getTuote());
            ostos.setYksikko(dto.getYksikko());
            Optional<OstosListaEntity> optOstosLista = ostosListaRepository.findById(id);
            if (optOstosLista.isPresent()) {
                ostos.setOstosLista(optOstosLista.get());
                ostosRepository.save(ostos);
                return ostosRepository.findById(ostos.getId());
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
