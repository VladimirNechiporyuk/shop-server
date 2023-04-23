package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CreateWalletDto;
import com.flamelab.shopserver.entities.Wallet;
import com.flamelab.shopserver.enums.NumberActionType;
import com.flamelab.shopserver.enums.WalletOwnerTypes;

import java.util.List;

public interface WalletsService {

    Wallet createWallet(CreateWalletDto createWalletDto);

    Wallet getWalletById(String walletId);

    Wallet getWalletByOwnerId(String ownerId);

    List<Wallet> getAllWallets();

    Wallet updateWalletAmount(String walletId, NumberActionType actionType, double amount);

    Wallet setWalletOwner(String walletId, WalletOwnerTypes ownerType, String ownerId);

    Wallet setWalletAmount(String walletId, double amount);

    boolean isWalletHasEnoughAmountForPurchase(String walletId, double purchasePrice);

    void deleteWallet(String walletId);
}
