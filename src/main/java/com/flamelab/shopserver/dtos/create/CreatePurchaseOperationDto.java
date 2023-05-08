package com.flamelab.shopserver.dtos.create;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CreatePurchaseOperationDto extends CreateCommonDto {

    private String productName;
    private Integer amount;
    private Double price;
    private String merchantId;
    private String merchantName;
    private String customerId;
    private String customerName;

}
