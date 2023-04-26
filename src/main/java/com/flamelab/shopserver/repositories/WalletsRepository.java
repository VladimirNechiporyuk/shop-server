package com.flamelab.shopserver.repositories;

import com.flamelab.shopserver.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletsRepository extends JpaRepository<Wallet, String> {

    Optional<Wallet> findByOwnerId(String ownerId);

}
