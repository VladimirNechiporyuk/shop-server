package com.flamelab.shopserver.entities;

import com.flamelab.shopserver.enums.OwnerType;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Wallet extends CommonEntity {

    private ObjectId ownerId;
    private OwnerType ownerType;
    private double amount;

    public Wallet(ObjectId ownerId, OwnerType ownerType, double amount) {
        super(ObjectId.get(), LocalDateTime.now(), LocalDateTime.now());
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.amount = amount;
    }
}
