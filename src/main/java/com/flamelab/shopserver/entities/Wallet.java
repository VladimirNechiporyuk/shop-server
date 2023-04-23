package com.flamelab.shopserver.entities;

import com.flamelab.shopserver.enums.WalletOwnerTypes;
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
@Table(name = "wallets")
public class Wallet extends CommonEntity {

    private String ownerId;
    private WalletOwnerTypes ownerType;
    private double amount;

}
