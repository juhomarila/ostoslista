package fi.ruoka.ostoslista.business;

import fi.ruoka.ostoslista.dto.TokenDto;
import fi.ruoka.ostoslista.dto.UserDto;

public interface UserBusiness {
    public TokenDto login(UserDto dto);

    public TokenDto refreshToken(String refreshToken);
}
