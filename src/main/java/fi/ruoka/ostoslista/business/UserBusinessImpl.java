package fi.ruoka.ostoslista.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.TokenDto;
import fi.ruoka.ostoslista.dto.UserDto;
import fi.ruoka.ostoslista.entity.UserEntity;
import fi.ruoka.ostoslista.repository.UserRepository;
import fi.ruoka.ostoslista.util.JwtUtil;
import fi.ruoka.ostoslista.util.PasswordUtil;

@Service
public class UserBusinessImpl implements UserBusiness {

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserBusinessImpl.class);

    @Override
    public TokenDto login(UserDto dto) {
        try {
            UserEntity user = userRepository.findByKayttajatunnus(dto.getKayttajatunnus());
            if (user != null && PasswordUtil.verifyPassword(dto.getSalasana(), user.getSalasana())) {
                JwtUtil jwtUtil = new JwtUtil(env);
                String accessToken = jwtUtil.generateToken(dto.getKayttajatunnus());
                String refreshToken = jwtUtil.generateRefreshToken(dto.getKayttajatunnus());
                return new TokenDto(true, "Kirjautuminen onnistui", accessToken, refreshToken);
            }
            logger.warn("Kirjautuminen epäonnistui");
            return new TokenDto(false, "Kirjautuminen epäonnistui", null, null);
        } catch (Exception e) {
            logger.error(ErrorMessages.USER_LOGIN_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return new TokenDto(false, "Kirjautuminen epäonnistui, järjestelmässä tapahtui virhe", null, null);
        }
    }

    @Override
    public TokenDto refreshToken(String refreshToken) {
        try {
            JwtUtil jwtUtil = new JwtUtil(env);
            if (jwtUtil.validateRefreshToken(refreshToken)) {
                String username = jwtUtil.extractUsername(refreshToken);
                String newAccessToken = jwtUtil.generateToken(username);
                String newRefreshToken = jwtUtil.generateRefreshToken(username);
                return new TokenDto(true, "Token uusittu onnistuneesti", newAccessToken, newRefreshToken);
            } else {
                logger.warn(ErrorMessages.USER_REFRESH_TOKEN_ERROR + "Tokenin uusiminen epäonnistui");   
                return new TokenDto(false, "Tokenin uusiminen epäonnistui", null, null);
            }
        } catch (Exception e) {
            logger.error(ErrorMessages.USER_REFRESH_TOKEN_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return new TokenDto(false, "Tokenin uusiminen epäonnistui, järjestelmässä tapahtui virhe", null, null);
        }

    }
}
