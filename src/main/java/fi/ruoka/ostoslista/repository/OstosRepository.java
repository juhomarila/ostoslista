package fi.ruoka.ostoslista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.ruoka.ostoslista.entity.OstosEntity;

@Repository
public interface OstosRepository extends JpaRepository<OstosEntity, Long> {
}
