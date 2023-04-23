package com.flamelab.shopserver.managers;

import com.flamelab.shopserver.dtos.create.wallet_operations.CreateShopDepositData;
import com.flamelab.shopserver.dtos.create.wallet_operations.CreateShopWithdrawData;
import com.flamelab.shopserver.dtos.create.wallet_operations.CreateUserDepositData;
import com.flamelab.shopserver.dtos.create.wallet_operations.CreateUserWithdrawData;
import com.flamelab.shopserver.dtos.transfer.TransferAuthTokenDto;
import com.flamelab.shopserver.dtos.transfer.TransferWalletDto;

import java.util.List;

public interface WalletsManager {

    TransferWalletDto getWalletById(TransferAuthTokenDto authToken, String walletId);

    List<TransferWalletDto> getAllWallets(TransferAuthTokenDto authToken);

    TransferWalletDto getWalletByOwnerId(TransferAuthTokenDto authToken, String ownerId);

    TransferWalletDto doDepositToUsersWallet(TransferAuthTokenDto authToken, CreateUserDepositData createUserDepositData);

    TransferWalletDto doDepositToShopWallet(TransferAuthTokenDto authToken, CreateShopDepositData createShopDepositData);

    TransferWalletDto doWithdrawFromUsersWallet(TransferAuthTokenDto authToken, CreateUserWithdrawData createWalletWithdrawData);

    TransferWalletDto doWithdrawFromShopsWallet(TransferAuthTokenDto authToken, CreateShopWithdrawData createShopWithdrawData);

    TransferWalletDto changeSelectedWalletAmount(TransferAuthTokenDto authToken, String walletId, double amount);
}
