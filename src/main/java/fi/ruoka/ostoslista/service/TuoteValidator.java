package fi.ruoka.ostoslista.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.TuoteDto;
import fi.ruoka.ostoslista.repository.TuoteRepository;

@Service
public class TuoteValidator {
    @Autowired
    private TuoteRepository tuoteRepository;

    public ValidationResult validate(TuoteDto dto, boolean isTuotePresent) {
        var errorMsg = new ArrayList<String>();

        if (isTuotePresent) {
            if (tuoteRepository.existsByTuote(dto.getTuote())) {
                errorMsg.add(ValidationError.VE006 + ".tuote");
            }
        }

        checkRequiredFields(dto, errorMsg, false);

        if (!errorMsg.isEmpty()) {
            return new ValidationResult(errorMsg, false);
        }
        return new ValidationResult(true);
    }

    public ValidationResult validateUpdate(TuoteDto dto, Long id) {
        var errorMsg = new ArrayList<String>();

        if (tuoteRepository.findById(id).isEmpty()) {
            errorMsg.add(ValidationError.VE005 + ".tuote");
        }

        checkRequiredFields(dto, errorMsg, false);

        if (!errorMsg.isEmpty()) {
            return new ValidationResult(errorMsg, false);
        }
        return new ValidationResult(true);
    }

    private void checkRequiredFields(TuoteDto dto, ArrayList<String> errorMsg, boolean isUpdate) {
        if (dto.getTuote() == null || dto.getTuote().length() < 1) {
            errorMsg.add(ValidationError.VE001 + ".tuote");
        }
        if (dto.getOsasto() == null) {
            errorMsg.add(ValidationError.VE001 + ".osasto");
        }
    }
}
