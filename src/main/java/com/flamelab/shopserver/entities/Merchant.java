package com.flamelab.shopserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.flamelab.shopserver.enums.Roles.MERCHANT;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class Merchant extends User {

    private String walletId;

    public Merchant() {
        super();
        super.setRole(MERCHANT);
    }

}
