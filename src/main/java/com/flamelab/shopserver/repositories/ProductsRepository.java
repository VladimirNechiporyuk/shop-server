package com.flamelab.shopserver.repositories;

import com.flamelab.shopserver.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Product, String> {

    List<Product> findByOwnerShopId(String ownerShopId);

    Optional<Product> findByOwnerShopIdAndName(String ownerShopId, String name);

    List<Product> findAllByOwnerShopIds(List<String> ownerShopIds);

}
