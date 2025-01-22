package fi.ruoka.ostoslista.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.UserDto;
import fi.ruoka.ostoslista.repository.UserRepository;

@Service
public class UserValidator {
    @Autowired
    private UserRepository userRepository;

    public ValidationResult validate(UserDto dto) {
        var errorMsg = new ArrayList<String>();

        if (!userRepository.existsByKayttajatunnus(dto.getKayttajatunnus())) {
            errorMsg.add(ValidationError.VE005 + ".kayttajatunnus");
        }

        checkRequiredFields(dto, errorMsg, false);

        if (!errorMsg.isEmpty()) {
            return new ValidationResult(errorMsg, false);
        }
        return new ValidationResult(true);
    }

    private void checkRequiredFields(UserDto dto, ArrayList<String> errorMsg, boolean isUpdate) {
        if (dto.getKayttajatunnus() == null || dto.getKayttajatunnus().length() < 1) {
            errorMsg.add(ValidationError.VE001 + ".kayttajatunnus");
        }
        if (dto.getSalasana() == null || dto.getSalasana().length() < 1) {
            errorMsg.add(ValidationError.VE001 + ".salasana");
        }
    }
}
