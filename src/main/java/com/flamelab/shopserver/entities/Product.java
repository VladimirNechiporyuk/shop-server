package com.flamelab.shopserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private String ownerShopId;
    private String name;
    private int amount;
    private double price;

}
