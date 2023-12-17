package com.flamelab.shopserver.mappers;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.transfer.TransferUserDto;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.entities.Wallet;
import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.utiles.RandomDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UsersMapper {

    private final RandomDataGenerator randomDataGenerator;
    private final PasswordEncoder passwordEncoder;

    public TransferUserDto mapToDto(User entity, Wallet wallet) {
        TransferUserDto dto = new TransferUserDto();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setLastUpdatedDate(entity.getLastUpdateDate());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setWalletId(wallet.getId());
        dto.setWalletAmount(wallet.getAmount());
        dto.setRole(Roles.valueOf(entity.getRole()));
        dto.setActive(entity.isActive());
        return dto;
    }

    public List<TransferUserDto> mapToDtoList(List<User> entityList, List<Wallet> walletList) {
        List<TransferUserDto> transferDtoList = new ArrayList<>();
        for (User user : entityList) {
            transferDtoList.add(
                    mapToDto(
                            user,
                            walletList.stream().filter(wallet -> wallet.getOwnerId().equals(user.getId())).findFirst().get()
                    ));
        }
        return transferDtoList;
    }

    public User mapToEntity(CreateUserDto createDto) {
        User entity = new User();
        entity.setId(randomDataGenerator.generateId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastUpdateDate(LocalDateTime.now());
        entity.setUsername(createDto.getName());
        entity.setPassword(createDto.getPassword());
        entity.setEmail(createDto.getEmail());
        entity.setRole(createDto.getRole().name());
        entity.setActive(false);
        return entity;
    }
}
