package fi.ruoka.ostoslista.business;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.OstosDto;
import fi.ruoka.ostoslista.dto.OstosListaDto;
import fi.ruoka.ostoslista.entity.OstosEntity;
import fi.ruoka.ostoslista.entity.OstosListaEntity;
import fi.ruoka.ostoslista.repository.OstosListaRepository;

@Service
public class OstosListaBusinessImpl implements OstosListaBusiness {

    @Autowired
    private OstosListaRepository repository;

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
        OstosListaEntity ostosLista = new OstosListaEntity();
        ostosLista = saveOstosLista(ostosLista, dto);
        return Optional.of(ostosLista);
    }

    @Override
    public Optional<OstosListaEntity> updateOstosLista(Long id, OstosListaDto dto) {
        Optional<OstosListaEntity> optOstosLista = repository.findById(id);
        if (optOstosLista.isPresent()) {
            OstosListaEntity ostosLista = optOstosLista.get();

            if (dto.getNimi() != null) {
                ostosLista.setNimi(dto.getNimi());
            }

            if (dto.getOstokset() != null) {
                List<OstosEntity> existingOstokset = ostosLista.getOstokset();
                List<OstosEntity> updatedOstokset = dto.getOstokset().stream()
                        .map(this::ostosToEntity)
                        .collect(Collectors.toList());

                for (OstosEntity updatedOstos : updatedOstokset) {
                    boolean found = false;
                    for (OstosEntity existingOstos : existingOstokset) {
                        if (existingOstos.getId() != null && existingOstos.getId().equals(updatedOstos.getId())) {
                            existingOstos.setMaara(updatedOstos.getMaara());
                            existingOstos.setTuote(updatedOstos.getTuote());
                            existingOstos.setYksikko(updatedOstos.getYksikko());
                            existingOstos.setOstosLista(updatedOstos.getOstosLista());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        updatedOstos.setOstosLista(ostosLista);
                        existingOstokset.add(updatedOstos);
                    }
                }
                ostosLista.setOstokset(existingOstokset);
            }

            repository.save(ostosLista);
            return Optional.of(ostosLista);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteOstosLista(Long id) {
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
        return entity;
    }

}
