package com.flamelab.shopserver.managers.impl;

import com.flamelab.shopserver.dtos.create.wallet_operations.CreateShopDepositData;
import com.flamelab.shopserver.dtos.create.wallet_operations.CreateShopWithdrawData;
import com.flamelab.shopserver.dtos.create.wallet_operations.CreateUserDepositData;
import com.flamelab.shopserver.dtos.create.wallet_operations.CreateUserWithdrawData;
import com.flamelab.shopserver.dtos.transfer.TransferAuthTokenDto;
import com.flamelab.shopserver.dtos.transfer.TransferWalletDto;
import com.flamelab.shopserver.entities.Shop;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.managers.WalletsManager;
import com.flamelab.shopserver.mappers.WalletMapper;
import com.flamelab.shopserver.services.ShopsService;
import com.flamelab.shopserver.services.UsersService;
import com.flamelab.shopserver.services.WalletsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.flamelab.shopserver.enums.NumberActionType.INCREASE;
import static com.flamelab.shopserver.enums.NumberActionType.DECREASE;

@Service
@RequiredArgsConstructor
public class WalletsManagerImpl implements WalletsManager {

    private final WalletsService walletsService;
    private final UsersService usersService;
    private final ShopsService shopsService;
    private final WalletMapper walletMapper;

    @Override
    public TransferWalletDto getWalletById(TransferAuthTokenDto authToken, String walletId) {
        return walletMapper.mapToDto(walletsService.getWalletById(walletId));
    }

    @Override
    public List<TransferWalletDto> getAllWallets(TransferAuthTokenDto authToken) {
        return walletMapper.mapToDtoList(walletsService.getAllWallets());
    }

    @Override
    public TransferWalletDto getWalletByOwnerId(TransferAuthTokenDto authToken, String ownerId) {
        return walletMapper.mapToDto(walletsService.getWalletByOwnerId(ownerId));
    }

    @Override
    public TransferWalletDto doDepositToUsersWallet(TransferAuthTokenDto authToken, CreateUserDepositData createUserDepositData) {
        User user = usersService.getUserById(authToken.getUserId());
        return walletMapper.mapToDto(walletsService.updateWalletAmount(user.getWalletId(), INCREASE, createUserDepositData.getValue()));
    }

    @Override
    public TransferWalletDto doDepositToShopWallet(TransferAuthTokenDto authToken, CreateShopDepositData createShopDepositData) {
        Shop shop = shopsService.getShopById(createShopDepositData.getShopId());
        return walletMapper.mapToDto(walletsService.updateWalletAmount(shop.getWalletId(), INCREASE, createShopDepositData.getValue()));
    }

    @Override
    public TransferWalletDto doWithdrawFromUsersWallet(TransferAuthTokenDto authToken, CreateUserWithdrawData createWalletWithdrawData) {
        User user = usersService.getUserById(authToken.getUserId());
        return walletMapper.mapToDto(walletsService.updateWalletAmount(user.getWalletId(), DECREASE, createWalletWithdrawData.getValue()));
    }

    @Override
    public TransferWalletDto doWithdrawFromShopsWallet(TransferAuthTokenDto authToken, CreateShopWithdrawData createShopWithdrawData) {
        Shop shop = shopsService.getShopById(createShopWithdrawData.getShopId());
        return walletMapper.mapToDto(walletsService.updateWalletAmount(shop.getWalletId(), DECREASE, createShopWithdrawData.getValue()));
    }

    @Override
    public TransferWalletDto changeSelectedWalletAmount(TransferAuthTokenDto authToken, String walletId, double amount) {
        return walletMapper.mapToDto(walletsService.setWalletAmount(walletId, amount));
    }
}
