package com.flamelab.shopserver.repositories;

import com.flamelab.shopserver.entities.AuthToken;
import com.flamelab.shopserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthToken, String> {

    Optional<AuthToken> findById(String id);

    Optional<AuthToken> findByUserId(String userId);

    Optional<AuthToken> findByToken(String token);

    Optional<AuthToken> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    void deleteByToken(String token);

}
