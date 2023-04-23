package com.flamelab.shopserver.dtos.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CreateTemporaryCodeDto {

    private String email;
    private int tempCode;

}
