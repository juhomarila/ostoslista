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
        // Check if the database is already populated
        if (tuoteRepository.count() > 0) {
            return; // Skip if there are already records
        }

        // Populate the database with enum values
        for (Tuotteet tuote : Tuotteet.values()) {
            if (!tuoteRepository.existsByTuote(tuote.name())) {
                TuoteEntity entity = new TuoteEntity();
                entity.setTuote(tuote.name());
                entity.setOsasto(tuote.getOsastoId());
                tuoteRepository.save(entity);
            }
        }

        System.out.println("TuoteEntity table populated successfully!");
    }
}
