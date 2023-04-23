package com.flamelab.shopserver.repositories;

import com.flamelab.shopserver.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopsRepository extends JpaRepository<Shop, String> {

    List<Shop> findAllByOwnerId(String ownerId);

    List<Shop> findAllByNameContaining(String name);


}
