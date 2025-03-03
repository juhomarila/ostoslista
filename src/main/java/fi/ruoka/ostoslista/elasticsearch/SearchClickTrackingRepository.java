package fi.ruoka.ostoslista.elasticsearch;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchClickTrackingRepository extends JpaRepository<SearchClickTracking, Long> {
    Optional<SearchClickTracking> findBySearchTermAndProductId(String searchTerm, Long productId);
}

