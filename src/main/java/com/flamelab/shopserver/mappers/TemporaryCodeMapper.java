package com.flamelab.shopserver.mappers;

import com.flamelab.shopserver.dtos.create.CreateTemporaryCodeDto;
import com.flamelab.shopserver.dtos.transfer.TransferTemporaryCodeDto;
import com.flamelab.shopserver.entities.TemporaryCode;
import com.flamelab.shopserver.utiles.RandomDataGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TemporaryCodeMapper {

    private final RandomDataGenerator randomDataGenerator;

    public TransferTemporaryCodeDto mapToDto(TemporaryCode entity) {
        TransferTemporaryCodeDto dto = new TransferTemporaryCodeDto();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdatedDate(entity.getLastUpdatedDate());
        dto.setTempCode(entity.getTempCode());
        return dto;
    }

    public List<TransferTemporaryCodeDto> mapToDtoList(List<TemporaryCode> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TemporaryCode mapToEntity(CreateTemporaryCodeDto createDto) {
        TemporaryCode entity = new TemporaryCode();
        entity.setId(randomDataGenerator.generateId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        entity.setTempCode(createDto.getTempCode());
        entity.setEmail(createDto.getEmail());
        return entity;
    }

}
