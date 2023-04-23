package com.flamelab.shopserver.dtos.create;

import com.flamelab.shopserver.enums.Roles;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto extends CreateCommonDto {

    private String email;
    private String name;
    private String password;
    private String passwordConfirmation;
    private Roles role;

}
