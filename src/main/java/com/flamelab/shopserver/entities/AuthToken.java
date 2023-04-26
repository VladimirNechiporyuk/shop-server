package com.flamelab.shopserver.entities;

import com.flamelab.shopserver.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class AuthToken {

    @Id
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private String userId;
    private String token;
    private String tokenType;
    private String email;
    private Roles role;
    private int usageAmount;

}
