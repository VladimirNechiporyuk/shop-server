package com.flamelab.shopserver.entities;

import com.flamelab.shopserver.enums.WalletOwnerTypes;
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
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private String ownerId;
    private WalletOwnerTypes ownerType;
    private double amount;

}
