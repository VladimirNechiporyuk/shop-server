package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserPasswordDto;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.mappers.UsersMapper;
import com.flamelab.shopserver.repositories.UsersRepository;
import com.flamelab.shopserver.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    @Override
    public User createUser(CreateUserDto createUserDto) {
        return usersRepository.save(usersMapper.mapToEntity(createUserDto));
    }

    @Override
    public User activateUser(String userId) {
        User user = getUserById(userId);
        user.setActive(true);
        return usersRepository.save(user);
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new ResourceException(NO_CONTENT, String.format("User with id '%s' does not exists", userId));
        }
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = usersRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new ResourceException(NO_CONTENT, String.format("User with email '%s' does not exists", email));
        }
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public List<User> getAllUsersByTextInParameters(String text) {
        Set<User> users = new HashSet<>();
        users.addAll(usersRepository.findAllByEmailContaining(text));
        users.addAll(usersRepository.findAllByRoleContaining(text));
        users.addAll(usersRepository.findAllByUsernameContaining(text));
        return new ArrayList<>(users);
    }

    @Override
    public User updateUserData(String userId, UpdateUserDto updateUserDto) {
        User userFromDb = getUserById(userId);
        boolean isDataChanged = false;
        if (updateUserDto.getUsername() != null && !updateUserDto.getUsername().isEmpty() && !userFromDb.getUsername().equals(updateUserDto.getUsername())) {
            userFromDb.setUsername(updateUserDto.getUsername());
            isDataChanged = true;
        }
        if (updateUserDto.getEmail() != null &&!updateUserDto.getEmail().isEmpty() && !userFromDb.getEmail().equals(updateUserDto.getEmail())) {
            userFromDb.setEmail(updateUserDto.getEmail());
            isDataChanged = true;
        }
        if (isDataChanged) {
            usersRepository.save(userFromDb);
        }
        return userFromDb;
    }

    @Override
    public User addWalletToUser(String userId, String walletId) {
        User user = getUserById(userId);
        user.setWalletId(walletId);
        return usersRepository.save(user);
    }

    @Override
    public void recoverPassword(String userId, String newPassword) {
        User user = getUserById(userId);
        user.setPassword(newPassword);
        usersRepository.save(user);
    }

    @Override
    public void updateUserPassword(User user, UpdateUserPasswordDto updateUserPasswordDto) {
        user.setPassword(updateUserPasswordDto.getNewPassword());
        usersRepository.save(user);
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public void deleteUser(String userId) {
        usersRepository.deleteById(userId);
    }

}
