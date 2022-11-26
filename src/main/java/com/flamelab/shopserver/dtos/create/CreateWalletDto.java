package com.flamelab.shopserver.dtos.create;

import com.flamelab.shopserver.enums.OwnerType;
import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CreateWalletDto extends CommonCreateDto {

    private ObjectId ownerId;
    private OwnerType ownerType;
    private double amount = 0;

}
