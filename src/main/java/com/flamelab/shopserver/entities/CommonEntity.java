package com.flamelab.shopserver.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommonEntity {

    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

}
