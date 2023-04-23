package com.flamelab.shopserver.entities;

import com.flamelab.shopserver.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class AuthToken extends CommonEntity {

    private String userId;
    private String token;
    private String tokenType;
    private String email;
    private Roles role;
    private int usageAmount;

}
