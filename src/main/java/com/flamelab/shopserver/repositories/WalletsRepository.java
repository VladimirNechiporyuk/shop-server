package com.flamelab.shopserver.repositories;

import com.flamelab.shopserver.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalletsRepository extends JpaRepository<Wallet, String> {

    Optional<Wallet> findByOwnerId(String ownerId);

}
