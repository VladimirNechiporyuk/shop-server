package com.flamelab.shopserver.dtos.update;

import com.flamelab.shopserver.enums.Roles;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UpdateUserAuthToken extends CommonUpdateDto {

    private String token;
    private String email;
    private Roles role;
    private Integer usageAmount;

}
