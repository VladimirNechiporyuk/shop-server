package com.flamelab.shopserver.managers.impl;

import com.flamelab.shopserver.dtos.create.CreateTemporaryCodeDto;
import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.create.CreateWalletDto;
import com.flamelab.shopserver.dtos.transfer.*;
import com.flamelab.shopserver.dtos.update.RecoverPasswordDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserPasswordDto;
import com.flamelab.shopserver.entities.CommonEntity;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.entities.Wallet;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.managers.UsersManager;
import com.flamelab.shopserver.mappers.PurchaseOperationMapper;
import com.flamelab.shopserver.mappers.UsersMapper;
import com.flamelab.shopserver.services.*;
import com.flamelab.shopserver.utiles.EmailTextProvider;
import com.flamelab.shopserver.utiles.RandomDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.flamelab.shopserver.enums.WalletOwnerTypes.USER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class UsersManagerImpl implements UsersManager {

    private final UsersService usersService;
    private final WalletsService walletsService;
    private final ShopsService shopsService;
    private final ProductsService productsService;
    private final PurchaseOperationService purchaseOperationService;
    private final UsersMapper usersMapper;
    private final PurchaseOperationMapper purchaseOperationMapper;
    private final SendEmailService sendEmailService;
    private final EmailTextProvider emailTextProvider;
    private final TemporaryCodeService temporaryCodeService;
    private final RandomDataGenerator randomDataGenerator;
    private final int START_USER_MONEY = 0;

    @Override
    public TransferUserDto createUser(CreateUserDto createUserDto) {
        if (!createUserDto.getPassword().equals(createUserDto.getPasswordConfirmation())) {
            throw new ResourceException(BAD_REQUEST, "Passwords are not equals");
        }
        Wallet wallet = walletsService.createWallet(new CreateWalletDto(START_USER_MONEY));
        User user = usersService.createUser(createUserDto);
        usersService.addWalletToUser(user.getId(), wallet.getId());
        walletsService.setWalletOwner(wallet.getId(), USER, user.getId());
        sendRegistrationTemporaryCodeToEmail(user.getEmail(), user.getId());
        return usersMapper.mapToDto(usersService.getUserById(user.getId()));
    }

    @Override
    public TransferUserDto createUserAdmin(TransferAuthTokenDto authToken, CreateUserDto createUserDto) {
        return usersMapper.mapToDto(usersService.createUser(createUserDto));
    }

    @Override
    public void sendTemporaryCodeToEmail(String email) {
        sendPasswordRecoveryTemporaryCodeToEmail(email);
    }

    @Override
    public TransferValidationResultDto verifyTempCode(TransferTemporaryCodeDto tempCode) {
        if (temporaryCodeService.validateTempCode(tempCode.getTempCode())) {
            return new TransferValidationResultDto(true);
        } else {
            throw new ResourceException(BAD_REQUEST, "Entered temporary code is not correct");
        }
    }

    @Override
    public TransferUserDto confirmRegistration(String userId, int tempCode) {
        verifyTempCode(new TransferTemporaryCodeDto(tempCode));
        return usersMapper.mapToDto(usersService.activateUser(userId));
    }

    @Override
    public TransferUserDto activateUser(TransferAuthTokenDto validateAuthToken, String userId) {
        return usersMapper.mapToDto(usersService.activateUser(userId));
    }

    @Override
    public TransferUserDto getUserById(TransferAuthTokenDto authToken, String userId) {
        return usersMapper.mapToDto(usersService.getUserById(userId));
    }

    @Override
    public List<TransferUserDto> getAllUsersByTextInParameters(TransferAuthTokenDto authToken, String text) {
        return usersMapper.mapToDtoList(usersService.getAllUsersByTextInParameters(text));
    }

    @Override
    public List<TransferUserDto> getAllUsers(TransferAuthTokenDto authToken) {
        return usersMapper.mapToDtoList(usersService.getAllUsers());
    }

    @Override
    public TransferUserDto updateUserData(TransferAuthTokenDto authToken, String userId, UpdateUserDto updateUserDto) {
        return usersMapper.mapToDto(usersService.updateUserData(userId, updateUserDto));
    }

    @Override
    public void recoverPassword(TransferAuthTokenDto authToken, RecoverPasswordDto recoverPasswordDto) {
        if (recoverPasswordDto.getNewPassword().equals(recoverPasswordDto.getRepeatNewPassword())) {
            usersService.recoverPassword(authToken.getUserId(), recoverPasswordDto.getNewPassword());
        } else {
            throw new ResourceException(BAD_REQUEST, "Passwords are not equals");
        }
    }

    @Override
    public void updateUserPassword(TransferAuthTokenDto authToken, UpdateUserPasswordDto updateUserPasswordDto) {
        User user = usersService.getUserById(authToken.getUserId());
        if (!updateUserPasswordDto.getCurrentPassword().equals(user.getPassword())) {
            throw new ResourceException(BAD_REQUEST, "Wrong user's password");
        }
        if (!updateUserPasswordDto.getNewPassword().equals(updateUserPasswordDto.getRepeatNewPassword())) {
            throw new ResourceException(BAD_REQUEST, "New password are not equals");
        }
        usersService.updateUserPassword(user, updateUserPasswordDto);
    }

    @Override
    public List<TransferPurchaseOperationDto> getPurchaseHistory(TransferAuthTokenDto authToken) {
        return purchaseOperationMapper.mapToDtoList(purchaseOperationService.getAllPurchaseOperationsByUser(authToken.getUserId()));
    }

    @Override
    public void deleteUser(TransferAuthTokenDto authToken, String userId) {
        usersService.deleteUser(userId);
        List<String> shopIds = shopsService.getAllShopsByOwnerId(userId).stream().map(CommonEntity::getId).toList();
        shopsService.deleteShops(shopIds);
        productsService.deleteProductsByShopIds(shopIds);
    }

    private void sendRegistrationTemporaryCodeToEmail(String email, String userId) {
        CreateTemporaryCodeDto createTemporaryCodeDto = new CreateTemporaryCodeDto(email, randomDataGenerator.generateTemporaryCode());
        if (usersService.isUserExistsByEmail(email)) {
            sendEmailService.sendEmail(
                    email,
                    "Registration confirmation",
                    emailTextProvider.provideConfirmRegistrationText(
                            userId,
                            temporaryCodeService.generateTemporaryCode(createTemporaryCodeDto).getTempCode()));
        }
    }

    private void sendPasswordRecoveryTemporaryCodeToEmail(String email) {
        CreateTemporaryCodeDto createTemporaryCodeDto = new CreateTemporaryCodeDto(email, randomDataGenerator.generateTemporaryCode());
        if (usersService.isUserExistsByEmail(email)) {
            sendEmailService.sendEmail(
                    email,
                    "Password Recovery",
                    emailTextProvider.providePasswordRecoverySendTempCodeText(
                            temporaryCodeService.generateTemporaryCode(createTemporaryCodeDto).getTempCode()));
        }
    }

}
