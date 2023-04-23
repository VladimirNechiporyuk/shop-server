package com.flamelab.shopserver.entities;

import com.flamelab.shopserver.enums.Roles;
import lombok.*;

import java.util.UUID;

import static com.flamelab.shopserver.enums.Roles.CUSTOMER;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends CommonEntity {

    private String username;
    private String email;
    private String password;
    private String walletId;
    private Roles role;
    private boolean isActive;

}
