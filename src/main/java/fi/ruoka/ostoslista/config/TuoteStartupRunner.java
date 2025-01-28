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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            UserEntity user = new UserEntity();
            user.setKayttajatunnus(env.getProperty("user.Entity"));
            user.setSalasana(PasswordUtil.hashPassword(env.getProperty("user.EntityPass")));
            userRepository.save(user);
            System.out.println("UserEntity table populated successfully!");
        }
        synchronizeTuoteRepository();
    }

    private void synchronizeTuoteRepository() {
        List<TuoteEntity> currentEntities = tuoteRepository.findAll();

        Set<String> enumTuotteet = new HashSet<>();
        for (Tuotteet tuote : Tuotteet.values()) {
            enumTuotteet.add(tuote.getTuote());
            TuoteEntity entity = tuoteRepository.findByTuote(tuote.getTuote())
                    .orElse(new TuoteEntity());

            if (entity.getId() != null) {

                if (!entity.getTuote().equals(tuote.getTuote()) ||
                        entity.getOsasto() == null || entity.getOsasto() != tuote.getOsastoId() ||
                        !entity.getYksikko().equals(tuote.getYksikkoOstoslistassa())) {
                    entity.setTuote(tuote.getTuote());
                    entity.setOsasto(tuote.getOsastoId());
                    entity.setYksikko(tuote.getYksikkoOstoslistassa());
                    tuoteRepository.save(entity);
                }
            } else {
                entity.setTuote(tuote.getTuote());
                entity.setOsasto(tuote.getOsastoId());
                entity.setYksikko(tuote.getYksikkoOstoslistassa());
                tuoteRepository.save(entity);
            }
        }

        List<TuoteEntity> entitiesToRemove = currentEntities.stream()
                .filter(entity -> !enumTuotteet.contains(entity.getTuote()))
                .collect(Collectors.toList());

        tuoteRepository.deleteAll(entitiesToRemove);

        System.out.println("TuoteEntity table synchronized successfully!");
    }
}