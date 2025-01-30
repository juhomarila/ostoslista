package fi.ruoka.ostoslista.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ruoka.ostoslista.entity.ReseptiEntity;
import fi.ruoka.ostoslista.entity.ReseptiOstoEntity;

public interface ReseptiOstoRepository extends JpaRepository<ReseptiOstoEntity, Long> {
    List<ReseptiOstoEntity> findByResepti(ReseptiEntity resepti);
}
