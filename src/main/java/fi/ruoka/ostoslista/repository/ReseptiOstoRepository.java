package fi.ruoka.ostoslista.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fi.ruoka.ostoslista.entity.ReseptiEntity;
import fi.ruoka.ostoslista.entity.ReseptiOstoEntity;

public interface ReseptiOstoRepository extends JpaRepository<ReseptiOstoEntity, Long> {

    List<ReseptiOstoEntity> findByResepti(ReseptiEntity resepti);

    @Query(value = """
    SELECT ro.resepti_entity_id, COUNT(ro.id) AS count
    FROM resepti_osto_entity ro
    WHERE EXTRACT(YEAR FROM ro.osto_aika) = :year
    GROUP BY ro.resepti_entity_id
    ORDER BY COUNT(ro.id) DESC
    LIMIT 1
    """, nativeQuery = true)
    List<Object[]> findMostBoughtReseptiIdAndCountByYear(@Param("year") Integer year);

    @Query(value = """
    SELECT DISTINCT EXTRACT(YEAR FROM osto_aika) 
    FROM resepti_osto_entity 
    ORDER BY EXTRACT(YEAR FROM osto_aika) DESC
    """, nativeQuery = true)
    List<Integer> findAllAvailableYears();
}
