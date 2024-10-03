package fi.ruoka.ostoslista.repository;

import fi.ruoka.ostoslista.entity.ReseptiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReseptiRepository extends JpaRepository<ReseptiEntity, Long> {
    boolean existsByNimi(String nimi);
}
