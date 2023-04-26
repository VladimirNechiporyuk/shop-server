package com.flamelab.shopserver.repositories;

import com.flamelab.shopserver.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopsRepository extends JpaRepository<Shop, String> {

    List<Shop> findAllByOwnerId(String ownerId);

    List<Shop> findAllByNameContaining(String name);


}
