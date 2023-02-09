package com.flamelab.shopserver.managers.impl;

import com.flamelab.shopserver.dtos.create.external.CreateUserDto;
import com.flamelab.shopserver.dtos.create.external.CreateWalletDto;
import com.flamelab.shopserver.dtos.transafer.TransferUserDto;
import com.flamelab.shopserver.dtos.transafer.TransferWalletDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserPasswordDto;
import com.flamelab.shopserver.entities.AuthToken;
import com.flamelab.shopserver.entities.Shop;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.entities.Wallet;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.exceptions.ShopHasNotEnoughProductsException;
import com.flamelab.shopserver.exceptions.UserHasNotEnoughMoneyException;
import com.flamelab.shopserver.internal_data.Product;
import com.flamelab.shopserver.managers.UsersManager;
import com.flamelab.shopserver.services.AuthService;
import com.flamelab.shopserver.services.ShopsService;
import com.flamelab.shopserver.services.UserService;
import com.flamelab.shopserver.services.WalletsService;
import com.flamelab.shopserver.utiles.MapperUtility;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.enums.AmountActionType.DECREASE;
import static com.flamelab.shopserver.enums.AmountActionType.INCREASE;
import static com.flamelab.shopserver.enums.OwnerType.USER;
import static com.flamelab.shopserver.enums.Roles.MERCHANT;
import static com.flamelab.shopserver.utiles.naming.FieldNames.OWNER_ID__FIELD_APPELLATION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.TOKEN__FIELD_APPELLATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class UsersManagerImpl implements UsersManager {

    private final UserService userService;
    private final ShopsService shopsService;
    private final AuthService authService;
    private final WalletsService walletsService;
    private final MapperUtility<User, TransferUserDto> userMapperFromEntityToTransferDto;
    private final MapperUtility<Wallet, TransferWalletDto> walletMapperFromEntityToTransferDto;

    @Override
    public TransferUserDto createUser(CreateUserDto createUserDto) {
        User user = userService.createEntity(createUserDto);
        int START_WALLET_AMOUNT = 0;
        Wallet wallet = walletsService.createEntity(new CreateWalletDto(user.getId(), USER, START_WALLET_AMOUNT));
        return userMapperFromEntityToTransferDto.map(
                userService.addWalletToUser(user.getId(), wallet.getId()),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferUserDto getUserById(ObjectId userId) {
        return userMapperFromEntityToTransferDto.map(
                userService.getEntityById(userId),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferUserDto getUserBy(Map<FieldNames, Object> criterias) {
        return userMapperFromEntityToTransferDto.map(
                userService.getEntityByCriterias(criterias),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public List<TransferUserDto> getAllUsersByCriterias(Map<FieldNames, Object> criterias) {
        return userMapperFromEntityToTransferDto.mapToList(
                userService.getAllEntitiesByCriterias(criterias),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public List<TransferUserDto> getAllUsers() {
        return userMapperFromEntityToTransferDto.mapToList(
                userService.getAllEntities(),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferWalletDto getUserWallet(ObjectId userId) {
        return walletMapperFromEntityToTransferDto.map(
                walletsService.getWalletByOwnerId(userId),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    @Override
    public TransferUserDto updateUserData(ObjectId userId, UpdateUserDto updateUserDto) {
        return userMapperFromEntityToTransferDto.map(
                userService.updateEntityById(userId, updateUserDto),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferWalletDto deposit(ObjectId userId, int amount) {
        User user = userService.getEntityById(userId);
        return walletMapperFromEntityToTransferDto.map(
                walletsService.updateWalletAmount(user.getWalletId(), INCREASE, amount),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    @Override
    public TransferUserDto buyProducts(ObjectId userId, ObjectId shopId, ProductName productName, int amount) {
        Product productDataFromShop = shopsService.getProductData(shopId, productName);
        User user = userService.getEntityById(userId);
        Shop shop = shopsService.getEntityById(shopId);
        if (productDataFromShop.getAmount() < amount) {
            throw new ShopHasNotEnoughProductsException(String.format("Shop with id '%s' has not enough amount of the product '%s'", shopId, productName));
        }
        double paymentMoney = productDataFromShop.getPrice() * amount;
        boolean isWalletHasEnoughAmount = walletsService.isWalletHasEnoughAmountByOwnerId(userId, paymentMoney);
        if (!isWalletHasEnoughAmount) {
            throw new UserHasNotEnoughMoneyException(String.format("User with id '%s' has not enough money for buy the '%s'", userId, productName));
        } else {
            walletsService.updateWalletAmount(user.getWalletId(), DECREASE, paymentMoney);
            walletsService.updateWalletAmount(shop.getWalletId(), INCREASE, paymentMoney);
            shopsService.decreaseProductsAmount(shopId, productName, amount);
            return userMapperFromEntityToTransferDto.map(
                    userService.addProductsToTheBucket(userId, productName, productDataFromShop.getPrice(), amount),
                    User.class,
                    TransferUserDto.class
            );
        }
    }

    @Override
    public void deleteUser(ObjectId userId, String authorization) {
        AuthToken token = authService.getToken(Map.of(TOKEN__FIELD_APPELLATION, authorization));
        if (!userId.toHexString().equals(token.getUserId())) {
            throw new ResourceException(BAD_REQUEST, "User can't delete other user");
        }
        TransferUserDto user = getUserById(userId);
        if (token.getRole().equals(MERCHANT)) {
            shopsService.deleteEntityByCriterias(Map.of(OWNER_ID__FIELD_APPELLATION, userId));
        }
        walletsService.deleteEntityById(user.getWalletId());
        userService.deleteEntityById(userId);
    }

    @Override
    public void updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto) {
        userService.updateUserPassword(updateUserPasswordDto);
    }
}
