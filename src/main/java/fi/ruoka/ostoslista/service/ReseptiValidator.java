package fi.ruoka.ostoslista.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.repository.ReseptiRepository;

@Service
public class ReseptiValidator {
    @Autowired
    private ReseptiRepository reseptiRepository;

    public ValidationResult validate(ReseptiDto dto, boolean isReseptiPresent) {
        var errorMsg = new ArrayList<String>();

        if (isReseptiPresent) {
            if (reseptiRepository.existsByNimi(dto.getNimi())) {
                errorMsg.add(ValidationError.VE006 + ".name");
            }
        }

        checkRequiredFields(dto, errorMsg);

        if (!errorMsg.isEmpty()) {
            return new ValidationResult(errorMsg, false);
        }
        return new ValidationResult(true);
    }

    // TODO update

    private void checkRequiredFields(ReseptiDto dto, ArrayList<String> errorMsg) {
        if (dto.getNimi() == null || dto.getNimi().length() < 1) {
            errorMsg.add(ValidationError.VE001 + ".nimi");
        }
        if (dto.getOhje() == null || dto.getOhje().length() < 1) {
            errorMsg.add(ValidationError.VE001 + ".ohje");
        }
        if (dto.getRuokaAineet() == null || dto.getRuokaAineet().isEmpty()) {
            errorMsg.add(ValidationError.VE001 + ".ruokaaineet");
        }
        if (dto.getRuokaAineet() != null) {
            dto.getRuokaAineet().forEach(ra -> {
                if (ra.getMaara() == null) {
                    errorMsg.add(ValidationError.VE001 + ".maara");
                }
                if (ra.getRuokaAine() == null || ra.getRuokaAine().length() < 1) {
                    errorMsg.add(ValidationError.VE001 + ".tuote");
                }
                if (ra.getYksikko() == null || ra.getYksikko().length() < 1) {
                    errorMsg.add(ValidationError.VE001 + ".yksikko");
                }
            });
        }
    }
}
