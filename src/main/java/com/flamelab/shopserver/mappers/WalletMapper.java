package com.flamelab.shopserver.mappers;

import com.flamelab.shopserver.dtos.create.CreateWalletDto;
import com.flamelab.shopserver.dtos.transfer.TransferWalletDto;
import com.flamelab.shopserver.entities.Wallet;
import com.flamelab.shopserver.utiles.RandomDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WalletMapper {

    private final RandomDataGenerator randomDataGenerator;

    public TransferWalletDto mapToDto(Wallet entity) {
        TransferWalletDto dto = new TransferWalletDto();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdatedDate(entity.getLastUpdatedDate());
        dto.setOwnerId(entity.getOwnerId());
        dto.setOwnerType(entity.getOwnerType());
        dto.setAmount(entity.getAmount());
        return dto;
    }

    public List<TransferWalletDto> mapToDtoList(List<Wallet> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Wallet mapToEntity(CreateWalletDto createDto) {
        Wallet wallet = new Wallet();
        wallet.setId(randomDataGenerator.generateId());
        wallet.setCreatedDate(LocalDateTime.now());
        wallet.setLastUpdatedDate(LocalDateTime.now());
        wallet.setAmount(createDto.getAmount());
        return wallet;
    }
}
