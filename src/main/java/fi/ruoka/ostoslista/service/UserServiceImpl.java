package fi.ruoka.ostoslista.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.business.UserBusiness;
import fi.ruoka.ostoslista.dto.TokenDto;
import fi.ruoka.ostoslista.dto.UserDto;
import fi.ruoka.ostoslista.logging.OstosListaLogger;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserBusiness business;

    @Autowired
    private UserValidator validator;

    private final OstosListaLogger logger;

    public UserServiceImpl(OstosListaLogger logger) {
        this.logger = logger;
    }

    @Override
    public ValidatedServiceResult<TokenDto> login(UserDto dto) {
        var vr = validator.validate(dto);
        if (!vr.validated) {
            logger.logValidationFailure(ValidationError.UE101 + vr.getErrorMsg());
            return new ValidatedServiceResult<>(null, vr);
        }
        var user = business.login(dto);
        if (!user.isSuccess()) {
            var errorMsg = new ArrayList<String>();
            errorMsg.add(user.getMessage());
            errorMsg.add(ValidationError.UE102);
            vr.setValidated(false);
            vr.setErrorMsg(errorMsg);
            return new ValidatedServiceResult<>(null, vr);
        }
        return new ValidatedServiceResult<>(user, vr);
    }

    @Override
    public ValidatedServiceResult<TokenDto> refreshToken(String refreshToken) {
        var refreshedToken = business.refreshToken(refreshToken);
        if (refreshedToken.isSuccess()) {
            return new ValidatedServiceResult<>(refreshedToken, new ValidationResult(null, true));
        }
        return new ValidatedServiceResult<>(refreshedToken,
                new ValidationResult(Arrays.asList("RefreshToken error"), false));
    }

}
