package com.flamelab.shopserver.dtos.create;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateWalletDto extends CreateCommonDto {

    private double amount;

}
