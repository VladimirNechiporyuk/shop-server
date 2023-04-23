package com.flamelab.shopserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class PurchaseOperation extends CommonEntity {

    private String productName;
    private int amount;
    private double price;
    // in case when shop buy products on the stock:
        // the customerId = shopId
        // merchantId = id of the stock - hardcoded value in the DB
    private String merchantId;
    private String customerId;

}
