package fi.ruoka.ostoslista.service;

import fi.ruoka.ostoslista.dto.TokenDto;
import fi.ruoka.ostoslista.dto.UserDto;

public interface UserService {
    ValidatedServiceResult<TokenDto> login(UserDto dto);

    ValidatedServiceResult<TokenDto> refreshToken(String refreshToken);
}
