package fi.ruoka.ostoslista.service;

import fi.ruoka.ostoslista.dto.LoginDto;
import fi.ruoka.ostoslista.dto.UserDto;

public interface UserService {
    ValidatedServiceResult<LoginDto> login(UserDto dto);

}
