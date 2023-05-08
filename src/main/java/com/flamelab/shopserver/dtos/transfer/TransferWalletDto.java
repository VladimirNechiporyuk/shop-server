package com.flamelab.shopserver.dtos.transfer;

import com.flamelab.shopserver.enums.WalletOwnerTypes;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransferWalletDto extends TransferCommonDto {

    private String ownerId;
    private WalletOwnerTypes ownerType;
    private String ownerName;
    private double amount;

}
