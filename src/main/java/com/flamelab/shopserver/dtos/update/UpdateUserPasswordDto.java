package com.flamelab.shopserver.dtos.update;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordDto extends UpdateCommonDto {

    private String currentPassword;
    private String newPassword;
    private String repeatNewPassword;

}
