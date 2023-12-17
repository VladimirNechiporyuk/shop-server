package com.flamelab.shopserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "purchase_history")
public class PurchaseOperation {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "amount")
    private int amount;
    @Column(name = "price")
    private double price;
    // in case when shop buy products on the stock:
    // the customerId = shopId
    // merchantId = id of the stock - hardcoded value in the DB
    @Column(name = "merchant_id")
    private String merchantId;
    @Column(name = "merchant_name")
    private String merchantName;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "customer_name")
    private String customerName;

}
