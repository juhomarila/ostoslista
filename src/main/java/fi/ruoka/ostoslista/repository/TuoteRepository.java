package fi.ruoka.ostoslista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ruoka.ostoslista.entity.TuoteEntity;

public interface TuoteRepository extends JpaRepository<TuoteEntity, Long> {
    boolean existsByTuote(String tuote);
}
