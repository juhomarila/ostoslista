package fi.ruoka.ostoslista.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import fi.ruoka.ostoslista.entity.TuoteEntity;
import fi.ruoka.ostoslista.entity.UserEntity;
import fi.ruoka.ostoslista.enums.Tuotteet;
import fi.ruoka.ostoslista.repository.TuoteRepository;
import fi.ruoka.ostoslista.repository.UserRepository;
import fi.ruoka.ostoslista.util.PasswordUtil;

@Component
public class TuoteStartupRunner implements CommandLineRunner {

    @Autowired
    private Environment env;

    private final TuoteRepository tuoteRepository;

    private final UserRepository userRepository;

    public TuoteStartupRunner(TuoteRepository tuoteRepository, UserRepository userRepository) {
        this.tuoteRepository = tuoteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() > 0) {
            return;
        }
        if (tuoteRepository.count() > 0) {
            return;
        }
        for (Tuotteet tuote : Tuotteet.values()) {
            if (!tuoteRepository.existsByTuote(tuote.getTuote())) {
                TuoteEntity entity = new TuoteEntity();
                entity.setTuote(tuote.getTuote());
                entity.setOsasto(tuote.getOsastoId());
                tuoteRepository.save(entity);
            }
        }

        System.out.println("TuoteEntity table populated successfully!");

        UserEntity user = new UserEntity();
        user.setKayttajatunnus(env.getProperty("user.Entity"));
        user.setSalasana(PasswordUtil.hashPassword(env.getProperty("user.EntityPass")));

        userRepository.save(user);

        System.out.println("UserEntity table populated successfully!");
    }
}
