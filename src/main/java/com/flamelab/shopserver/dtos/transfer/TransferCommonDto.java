package com.flamelab.shopserver.dtos.transfer;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class TransferCommonDto {

    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

}
