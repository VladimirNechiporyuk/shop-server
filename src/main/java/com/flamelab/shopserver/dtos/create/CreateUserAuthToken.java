package com.flamelab.shopserver.dtos.create;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CreateUserAuthToken extends CommonCreateDto {

    private String email;
    private String password;

}
