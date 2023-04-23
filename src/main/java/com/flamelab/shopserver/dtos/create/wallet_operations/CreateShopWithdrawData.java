package com.flamelab.shopserver.dtos.create.wallet_operations;

import com.flamelab.shopserver.dtos.create.CreateCommonDto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateShopWithdrawData extends CreateCommonDto {

    private String shopId;
    private double value;

}
