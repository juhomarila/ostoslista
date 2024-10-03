package fi.ruoka.ostoslista.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.OstosListaDto;

@Service
public class OstosListaValidator {

    public ValidationResult validate(OstosListaDto dto) {
        var errorMsg = new ArrayList<String>();

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
        if (dto.getOstokset() == null || dto.getOstokset().isEmpty()) {
            errorMsg.add(ValidationError.VE001 + ".ostokset");
        }
    }

}
