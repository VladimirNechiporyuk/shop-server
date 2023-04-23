package com.flamelab.shopserver.dtos.transfer;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransferTemporaryCodeDto extends TransferCommonDto {

    private int tempCode;

}
