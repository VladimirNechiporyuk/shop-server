package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CreateTemporaryCodeDto;
import com.flamelab.shopserver.entities.TemporaryCode;

public interface TemporaryCodeService {

    TemporaryCode generateTemporaryCode(CreateTemporaryCodeDto createTemporaryCodeDto);

    TemporaryCode getTemporaryCodeByCode(int tempCode);

    TemporaryCode getTemporaryCodeByEmail(String email);

    boolean validateTempCode(int tempCode);

    void deleteTemporaryCode(int tempCode);
}
