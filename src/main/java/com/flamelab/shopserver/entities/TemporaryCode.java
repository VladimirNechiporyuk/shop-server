package com.flamelab.shopserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "temp_codes")
public class TemporaryCode {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @Column(name = "email")
    private String email;
    @Column(name = "temp_code")
    private int tempCode;

}
