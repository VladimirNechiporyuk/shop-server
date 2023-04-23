package com.flamelab.shopserver.mappers;

import com.flamelab.shopserver.dtos.transfer.TransferAuthTokenDto;
import com.flamelab.shopserver.entities.AuthToken;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.utiles.RandomDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.flamelab.shopserver.enums.AuthTokenType.BEARER;

@Component
@RequiredArgsConstructor
public class AuthTokenMapper {

    private final RandomDataGenerator randomDataGenerator;

    public TransferAuthTokenDto mapToDto(AuthToken entity) {
        TransferAuthTokenDto dto = new TransferAuthTokenDto();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdatedDate(entity.getLastUpdatedDate());
        dto.setUserId(entity.getUserId());
        dto.setToken(entity.getToken());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        return dto;
    }

    public List<TransferAuthTokenDto> mapToDtoList(List<AuthToken> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public AuthToken generateAuthToken(User user) {
        AuthToken entity = new AuthToken();
        entity.setId(randomDataGenerator.generateId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        entity.setUserId(user.getId());
        entity.setToken(randomDataGenerator.generateAuthToken());
        entity.setTokenType(BEARER.getTypeName());
        entity.setEmail(user.getEmail());
        entity.setRole(user.getRole());
        entity.setUsageAmount(0);
        return entity;
    }

}
