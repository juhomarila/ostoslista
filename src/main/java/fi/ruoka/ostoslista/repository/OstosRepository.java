package fi.ruoka.ostoslista.repository;

import fi.ruoka.ostoslista.entity.OstosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OstosRepository extends JpaRepository<OstosEntity, Long> {

}
