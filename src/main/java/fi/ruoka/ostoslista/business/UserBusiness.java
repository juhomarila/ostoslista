package fi.ruoka.ostoslista.business;

import fi.ruoka.ostoslista.dto.LoginDto;
import fi.ruoka.ostoslista.dto.UserDto;

public interface UserBusiness {
    public LoginDto login(UserDto dto);
}
