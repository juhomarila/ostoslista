package fi.ruoka.ostoslista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.ruoka.ostoslista.entity.RuokaAineEntity;

@Repository
public interface RuokaAineRepository extends JpaRepository<RuokaAineEntity, Long> {

}
