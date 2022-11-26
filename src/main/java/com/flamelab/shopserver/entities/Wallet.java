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

}
