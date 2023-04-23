package com.flamelab.shopserver.dtos.transfer;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransferPurchaseOperationDto extends TransferCommonDto{

    private String productName;
    private int amount;
    private double price;
    private String merchantId;
    private String customerId;

}
