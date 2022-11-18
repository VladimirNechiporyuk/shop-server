package com.flamelab.shopserver.dtos.create;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CreateUserDto extends CommonCreateDto {

    private String name;
    private String email;

}
