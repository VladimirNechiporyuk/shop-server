package com.flamelab.shopserver.mappers;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.dtos.transfer.TransferShopDto;
import com.flamelab.shopserver.entities.Shop;
import com.flamelab.shopserver.utiles.RandomDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShopMapper {

    private final RandomDataGenerator randomDataGenerator;

    public TransferShopDto mapToDto(Shop entity) {
        TransferShopDto dto = new TransferShopDto();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdatedDate(entity.getLastUpdatedDate());
        dto.setName(entity.getName());
        dto.setWalletId(entity.getWalletId());
        dto.setOwnerId(entity.getOwnerId());
        return dto;
    }

    public List<TransferShopDto> mapToDtoList(List<Shop> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Shop mapToEntity(CreateShopDto createDto, String walletId, String userId) {
        Shop entity = new Shop();
        entity.setId(randomDataGenerator.generateId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        entity.setName(createDto.getName());
        entity.setWalletId(walletId);
        entity.setOwnerId(userId);
        return entity;
    }

}
