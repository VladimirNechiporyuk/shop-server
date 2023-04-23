package com.flamelab.shopserver.dtos.create;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto extends CreateCommonDto {

    private String ownerShopId;
    private String name;
    private Integer amount;
    private Double price;

}
