package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.create.CreateTemporaryCodeDto;
import com.flamelab.shopserver.entities.TemporaryCode;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.mappers.TemporaryCodeMapper;
import com.flamelab.shopserver.repositories.TemporaryCodeRepository;
import com.flamelab.shopserver.services.TemporaryCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Service
@RequiredArgsConstructor
public class TemporaryCodeServiceImpl implements TemporaryCodeService {

    private final TemporaryCodeRepository temporaryCodeRepository;
    private final TemporaryCodeMapper temporaryCodeMapper;

    @Override
    public TemporaryCode generateTemporaryCode(CreateTemporaryCodeDto createTemporaryCodeDto) {
        return temporaryCodeRepository.save(temporaryCodeMapper.mapToEntity(createTemporaryCodeDto));
    }

    @Override
    public TemporaryCode getTemporaryCodeByCode(int tempCode) {
        Optional<TemporaryCode> optionalTemporaryCode = temporaryCodeRepository.findByTempCode(tempCode);
        if (optionalTemporaryCode.isPresent()) {
            return optionalTemporaryCode.get();
        } else {
            throw new ResourceException(NO_CONTENT, String.format("Temporary code '%s' does not exists", tempCode));
        }
    }

    @Override
    public TemporaryCode getTemporaryCodeByEmail(String email) {
        Optional<TemporaryCode> optionalTemporaryCode = temporaryCodeRepository.findByTempEmail(email);
        if (optionalTemporaryCode.isPresent()) {
            return optionalTemporaryCode.get();
        } else {
            throw new ResourceException(NO_CONTENT, String.format("Temporary code for email '%s' does not exists", email));
        }
    }

    @Override
    public boolean validateTempCode(int tempCode) {
        return temporaryCodeRepository.existsByTempCode(tempCode);
    }

}
