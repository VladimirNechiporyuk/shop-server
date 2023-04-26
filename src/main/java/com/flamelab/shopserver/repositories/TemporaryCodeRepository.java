package com.flamelab.shopserver.repositories;

import com.flamelab.shopserver.entities.TemporaryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporaryCodeRepository extends JpaRepository<TemporaryCode, String> {

    Optional<TemporaryCode> findByEmail(String email);

    Optional<TemporaryCode> findByTempCode(int tempCode);

    boolean existsByTempCode(int tempCode);

}
