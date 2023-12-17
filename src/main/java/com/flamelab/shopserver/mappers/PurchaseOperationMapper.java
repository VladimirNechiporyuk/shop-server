package com.flamelab.shopserver.mappers;

import com.flamelab.shopserver.dtos.create.CreatePurchaseOperationDto;
import com.flamelab.shopserver.dtos.transfer.TransferPurchaseOperationDto;
import com.flamelab.shopserver.entities.PurchaseOperation;
import com.flamelab.shopserver.utiles.RandomDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PurchaseOperationMapper {

    private final RandomDataGenerator randomDataGenerator;

    public TransferPurchaseOperationDto mapToDto(PurchaseOperation entity) {
        TransferPurchaseOperationDto dto = new TransferPurchaseOperationDto();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdatedDate(entity.getLastUpdateDate());
        dto.setProductName(entity.getProductName());
        dto.setAmount(entity.getAmount());
        dto.setPrice(entity.getPrice());
        dto.setCustomerId(entity.getCustomerId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setMerchantId(entity.getMerchantId());
        dto.setMerchantName(entity.getMerchantName());
        return dto;
    }

    public List<TransferPurchaseOperationDto> mapToDtoList(List<PurchaseOperation> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PurchaseOperation mapToEntity(CreatePurchaseOperationDto createDto) {
        PurchaseOperation entity = new PurchaseOperation();
        entity.setId(randomDataGenerator.generateId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdateDate(LocalDateTime.now());
        entity.setProductName(createDto.getProductName());
        entity.setAmount(createDto.getAmount());
        entity.setPrice(createDto.getPrice());
        entity.setCustomerId(createDto.getCustomerId());
        entity.setCustomerName(createDto.getCustomerName());
        entity.setMerchantId(createDto.getMerchantId());
        entity.setMerchantName(createDto.getMerchantName());
        return entity;
    }
}
