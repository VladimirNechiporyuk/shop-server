package com.flamelab.shopserver.dtos.update;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UpdateUserPasswordDto extends CommonUpdateDto{

    private String email;
    private String oldPassword;
    private String password;
    private String confirmPassword;

}
