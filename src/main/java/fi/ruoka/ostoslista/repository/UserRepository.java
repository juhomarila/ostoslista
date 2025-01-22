package fi.ruoka.ostoslista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.ruoka.ostoslista.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByKayttajatunnus(String kayttajatunnus);

    UserEntity findBySalasana(String salasana);

    UserEntity findByKayttajatunnus(String kayttajatunnus);
}
