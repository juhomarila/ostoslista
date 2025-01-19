package fi.ruoka.ostoslista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.ruoka.ostoslista.entity.OstosListaEntity;

@Repository
public interface OstosListaRepository extends JpaRepository<OstosListaEntity, Long> {

}