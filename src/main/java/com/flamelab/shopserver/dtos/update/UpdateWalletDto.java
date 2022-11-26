package com.flamelab.shopserver.dtos.update;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UpdateWalletDto extends CommonUpdateDto {

    private double amount;

}
