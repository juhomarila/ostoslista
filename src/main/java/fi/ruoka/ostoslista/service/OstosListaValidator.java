package fi.ruoka.ostoslista.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.OstosListaDto;
import fi.ruoka.ostoslista.repository.OstosListaRepository;

@Service
public class OstosListaValidator {
    @Autowired
    private OstosListaRepository ostosListaRepository;

    public ValidationResult validate(OstosListaDto dto) {
        var errorMsg = new ArrayList<String>();

        checkRequiredFields(dto, errorMsg);

        if (!errorMsg.isEmpty()) {
            return new ValidationResult(errorMsg, false);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateUpdate(OstosListaDto dto, Long id) {
        var errorMsg = new ArrayList<String>();

        if (ostosListaRepository.findById(id).isEmpty()) {
            errorMsg.add(ValidationError.VE005 + ".ostoslista");
        }

        checkRequiredFields(dto, errorMsg);

        if (!errorMsg.isEmpty()) {
            return new ValidationResult(errorMsg, false);
        }
        return new ValidationResult(true);
    }

    private void checkRequiredFields(OstosListaDto dto, ArrayList<String> errorMsg) {
        if (dto.getNimi() == null || dto.getNimi().length() < 1) {
            errorMsg.add(ValidationError.VE001 + ".nimi");
        }
    }

}
