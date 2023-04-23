package com.flamelab.shopserver.dtos.update;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto extends UpdateCommonDto {

    private String name;

}
