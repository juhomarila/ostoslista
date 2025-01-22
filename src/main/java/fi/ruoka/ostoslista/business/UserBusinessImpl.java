package fi.ruoka.ostoslista.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.dto.LoginDto;
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
    public LoginDto login(UserDto dto) {
        try {
            UserEntity user = userRepository.findByKayttajatunnus(dto.getKayttajatunnus());
            if (user != null && PasswordUtil.verifyPassword(dto.getSalasana(), user.getSalasana())) {
                JwtUtil jwtUtil = new JwtUtil(env);
                String token = jwtUtil.generateToken(dto.getKayttajatunnus());
                return new LoginDto(true, "Kirjautuminen onnistui", token);
            }
            return new LoginDto(false, "Kirjautuminen epäonnistui", null);
        } catch (Exception e) {
            logger.error(ErrorMessages.USER_LOGIN_ERROR + e.getMessage(), e);
            e.printStackTrace();
            return new LoginDto(false, "Kirjautuminen epäonnistui, järjestelmässä tapahtui virhe", null);
        }
    }
}
