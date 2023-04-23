package com.flamelab.shopserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

import static com.flamelab.shopserver.enums.Roles.ADMIN;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class Admin extends User {

    public Admin() {
        super();
    }

}
