package fi.ruoka.ostoslista.repository;

import fi.ruoka.ostoslista.entity.OstosListaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OstosListaRepository extends JpaRepository<OstosListaEntity, Long> {

}