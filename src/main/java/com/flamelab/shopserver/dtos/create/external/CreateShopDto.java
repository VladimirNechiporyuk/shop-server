package com.flamelab.shopserver.dtos.create.external;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CreateShopDto extends CommonCreateDto {

    private String name;

}
