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
@Table(name = "wallets")
public class Wallet {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @Column(name = "owner_id")
    private String ownerId;
    @Column(name = "owner_type")
    private String ownerType;
    @Column(name = "owner_name")
    private String ownerName;
    @Column(name = "amount")
    private double amount;

}
