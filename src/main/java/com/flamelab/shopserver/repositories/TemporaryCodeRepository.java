package com.flamelab.shopserver.repositories;

import com.flamelab.shopserver.entities.TemporaryCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemporaryCodeRepository extends JpaRepository<TemporaryCode, String> {

    Optional<TemporaryCode> findByTempEmail(String email);

    Optional<TemporaryCode> findByTempCode(int tempCode);

    boolean existsByTempCode(int tempCode);

}
