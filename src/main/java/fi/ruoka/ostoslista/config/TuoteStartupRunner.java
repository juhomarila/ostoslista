package fi.ruoka.ostoslista.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fi.ruoka.ostoslista.entity.TuoteEntity;
import fi.ruoka.ostoslista.enums.Tuotteet;
import fi.ruoka.ostoslista.repository.TuoteRepository;

@Component
public class TuoteStartupRunner implements CommandLineRunner {

    private final TuoteRepository tuoteRepository;

    @Autowired
    public TuoteStartupRunner(TuoteRepository tuoteRepository) {
        this.tuoteRepository = tuoteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
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
    }
}
