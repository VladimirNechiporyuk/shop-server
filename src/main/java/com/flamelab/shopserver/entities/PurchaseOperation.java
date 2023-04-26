package com.flamelab.shopserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class PurchaseOperation {

    @Id
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private String productName;
    private int amount;
    private double price;
    // in case when shop buy products on the stock:
    // the customerId = shopId
    // merchantId = id of the stock - hardcoded value in the DB
    private String merchantId;
    private String customerId;

}
