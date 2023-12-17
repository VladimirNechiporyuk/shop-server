package com.flamelab.shopserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class AuthToken {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "token")
    private String token;
    @Column(name = "tokenType")
    private String tokenType;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    private String role;
    @Column(name = "usage_amount")
    private int usageAmount;

}
