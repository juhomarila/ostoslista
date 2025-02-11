package fi.ruoka.ostoslista.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ruoka.ostoslista.entity.TuoteEntity;

public interface TuoteRepository extends JpaRepository<TuoteEntity, Long> {
    boolean existsByTuote(String tuote);

    Optional<TuoteEntity> findByTuote(String tuote);

    List<TuoteEntity> findByActiveTrue();
}
