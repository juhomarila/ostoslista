package fi.ruoka.ostoslista.service;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import fi.ruoka.ostoslista.dto.OstosDto;

@Component
public class OstosValidator {

    public ValidationResult validate(OstosDto dto) {
        var errorMsg = new ArrayList<String>();

        checkRequiredFields(dto, errorMsg);

        if (!errorMsg.isEmpty()) {
            return new ValidationResult(errorMsg, false);
        }
        return new ValidationResult(true);
    }

    private void checkRequiredFields(OstosDto dto, ArrayList<String> errorMsg) {
        if (dto.getTuote() == null || dto.getTuote().length() < 1) {
            errorMsg.add(ValidationError.VE001 + ".nimi");
        }
        if (dto.getMaara() == null) {
            errorMsg.add(ValidationError.VE001 + ".maara");
        }
        if (dto.getYksikko() == null || dto.getYksikko().length() < 1) {
            errorMsg.add(ValidationError.VE001 + ".yksikko");
        }
        if (dto.getOstosListaId() == null) {
            errorMsg.add(ValidationError.VE001 + ".ostosListaId");
        }
    }
}
