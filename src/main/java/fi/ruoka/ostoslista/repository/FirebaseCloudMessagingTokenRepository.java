package fi.ruoka.ostoslista.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import fi.ruoka.ostoslista.entity.FirebaseCloudMessagingTokenEntity;

public interface FirebaseCloudMessagingTokenRepository extends JpaRepository<FirebaseCloudMessagingTokenEntity, Long> {
    Optional<FirebaseCloudMessagingTokenEntity> findByToken(String token);
    
}
