package fi.ruoka.ostoslista.business;

import java.util.Optional;

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

    @Override
    public boolean deleteOstos(Long id) {
        ostosRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean addOstos(Long id, OstosDto dto) {
        OstosEntity ostos = new OstosEntity();
        ostos.setMaara(dto.getMaara());
        ostos.setTuote(dto.getTuote());
        ostos.setYksikko(dto.getYksikko());
        Optional<OstosListaEntity> optOstosLista = ostosListaRepository.findById(dto.getOstosListaId());
        if (optOstosLista.isPresent()) {
            ostos.setOstosLista(optOstosLista.get());
            ostosRepository.save(ostos);
            return true;
        }
        return false;
    }
}
