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

        if (userRepository.count() == 0) {
            String users = env.getProperty("user.Entity");
            String passwords = env.getProperty("user.EntityPass");

            if (users != null && passwords != null) {
                String[] userArray = users.split(",");
                String[] passwordArray = passwords.split(",");

                if (userArray.length != passwordArray.length) {
                    throw new IllegalArgumentException("Mismatch between number of users and passwords in .env file.");
                }

                for (int i = 0; i < userArray.length; i++) {
                    UserEntity user = new UserEntity();
                    user.setKayttajatunnus(userArray[i].trim());
                    user.setSalasana(PasswordUtil.hashPassword(passwordArray[i].trim()));
                    userRepository.save(user);
                }
            }

            System.out.println("UserEntity table populated successfully with multiple users!");
        }
        synchronizeTuoteRepository();
    }

    private void synchronizeTuoteRepository() {
        for (Tuotteet tuote : Tuotteet.values()) {
            TuoteEntity entity = tuoteRepository.findByTuote(tuote.getTuote())
                    .orElse(new TuoteEntity());

            if (entity.getId() != null) {

                if (!entity.getTuote().equals(tuote.getTuote()) ||
                        entity.getOsasto() == null || entity.getOsasto() != tuote.getOsastoId() ||
                        !entity.getYksikko().equals(tuote.getYksikkoOstoslistassa())) {
                    entity.setTuote(tuote.getTuote());
                    entity.setOsasto(tuote.getOsastoId());
                    entity.setYksikko(tuote.getYksikkoOstoslistassa());
                    entity.setOstoKerrat(entity.getOstoKerrat());
                    entity.setKplOstettu(0);
                    tuoteRepository.save(entity);
                }
            } else {
                entity.setTuote(tuote.getTuote());
                entity.setOsasto(tuote.getOsastoId());
                entity.setYksikko(tuote.getYksikkoOstoslistassa());
                entity.setOstoKerrat(0);
                entity.setKplOstettu(0);
                tuoteRepository.save(entity);
            }
        }

        System.out.println("TuoteEntity table synchronized successfully!");
    }
}