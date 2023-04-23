package com.flamelab.shopserver.mappers;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.transfer.TransferUserDto;
import com.flamelab.shopserver.entities.Admin;
import com.flamelab.shopserver.entities.Customer;
import com.flamelab.shopserver.entities.Merchant;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.utiles.RandomDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.flamelab.shopserver.enums.Roles.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
@RequiredArgsConstructor
public class UsersMapper {

    private final RandomDataGenerator randomDataGenerator;

    public TransferUserDto mapToDto(User entity) {
        TransferUserDto dto = new TransferUserDto();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdatedDate(entity.getLastUpdatedDate());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        if (entity.getRole().equals(ADMIN)) {
            dto.setWalletId(null);
        } else {
            dto.setWalletId(entity.getWalletId());
        }
        dto.setRole(entity.getRole());
        return dto;
    }

    public List<TransferUserDto> mapToDtoList(List<User> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public User mapToEntity(CreateUserDto createDto) {
        User entity;
        if (createDto.getRole().equals(ADMIN)) {
            entity = new Admin();
        } else if (createDto.getRole().equals(MERCHANT)) {
            entity = new Merchant();
        } else if (createDto.getRole().equals(CUSTOMER)) {
            entity = new Customer();
        } else {
            throw new ResourceException(BAD_REQUEST, String.format("The provided role '%s' does not exists.", createDto.getRole()));
        }
        entity.setId(randomDataGenerator.generateId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdatedDate(LocalDateTime.now());
        entity.setUsername(createDto.getName());
        entity.setPassword(createDto.getPassword());
        entity.setEmail(createDto.getEmail());
        entity.setRole(createDto.getRole());
        entity.setActive(false);
        return entity;
    }
}
