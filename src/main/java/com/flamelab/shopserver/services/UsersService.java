package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserPasswordDto;
import com.flamelab.shopserver.entities.User;

import java.util.List;

public interface UsersService {

    User createUser(CreateUserDto createUserDto);

    User activateUser(String userId);

    User getUserById(String userId);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> getAllUsersByTextInParameters(String text);

    User updateUserData(String userId, UpdateUserDto updateUserDto);

    User addWalletToUser(String userId, String walletId);

    void recoverPassword(String userId, String newPassword);

    void updateUserPassword(User user, UpdateUserPasswordDto updateUserPasswordDto);

    boolean isUserExistsByEmail(String email);

    void deleteUser(String userId);
}
