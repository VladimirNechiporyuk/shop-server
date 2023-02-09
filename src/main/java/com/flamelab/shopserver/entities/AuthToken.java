package com.flamelab.shopserver.entities;

import com.flamelab.shopserver.enums.Roles;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AuthToken extends CommonEntity {

    private String userId;
    private String token;
    private String tokenType;
    private String email;
    private Roles role;
    private Integer usageAmount;

}
