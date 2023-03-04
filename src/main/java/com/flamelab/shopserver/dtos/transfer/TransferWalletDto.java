package com.flamelab.shopserver.dtos.transfer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flamelab.shopserver.enums.OwnerType;
import com.flamelab.shopserver.utiles.serializers.StringJsonSerializer;
import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TransferWalletDto extends CommonTransferDto {

    @JsonSerialize(using = StringJsonSerializer.class)
    private ObjectId ownerId;
    private OwnerType ownerType;
    private double amount;

}
