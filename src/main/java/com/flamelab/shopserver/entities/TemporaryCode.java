package com.flamelab.shopserver.entities;

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
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private String email;
    private int tempCode;

}
