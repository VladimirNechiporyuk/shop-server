package com.flamelab.shopserver.dtos.update;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.enums.ProductName;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AddProductDto extends CommonCreateDto {

    private ProductName name;
    private double cost;
    private int amount;

}
