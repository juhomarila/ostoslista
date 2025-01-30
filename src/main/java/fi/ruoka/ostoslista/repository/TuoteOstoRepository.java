package fi.ruoka.ostoslista.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ruoka.ostoslista.entity.TuoteEntity;
import fi.ruoka.ostoslista.entity.TuoteOstoEntity;

public interface TuoteOstoRepository extends JpaRepository<TuoteOstoEntity, Long> {
    List<TuoteOstoEntity> findByTuote(TuoteEntity tuote);
}
