package com.flamelab.shopserver.dtos.update;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class UpdateUserDto extends UpdateCommonDto {

    private String username;
    private String email;

}
