package com.flamelab.shopserver.dtos.transfer;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransferProductDto extends TransferCommonDto {

    private String name;
    private int amount;
    private double price;

}
