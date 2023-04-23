package com.flamelab.shopserver.mappers;

import com.flamelab.shopserver.dtos.create.CreateProductDto;
import com.flamelab.shopserver.dtos.transfer.TransferProductDto;
import com.flamelab.shopserver.entities.Product;
import com.flamelab.shopserver.utiles.RandomDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final RandomDataGenerator randomDataGenerator;

    public TransferProductDto mapToDto(Product entity) {
        TransferProductDto dto = new TransferProductDto();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdatedDate(entity.getLastUpdatedDate());
        dto.setName(entity.getName());
        dto.setAmount(entity.getAmount());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    public List<TransferProductDto> mapToDtoList(List<Product> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Product mapToEntity(CreateProductDto createDto) {
        Product entity = new Product();
        entity.setId(randomDataGenerator.generateId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        entity.setOwnerShopId(createDto.getOwnerShopId());
        entity.setName(createDto.getName());
        entity.setAmount(createDto.getAmount());
        entity.setPrice(createDto.getPrice());
        return entity;
    }
}
