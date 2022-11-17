package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.transafer.TransferWalletDto;
import com.flamelab.shopserver.enums.AmountActionType;
import com.flamelab.shopserver.enums.OwnerType;
import org.bson.types.ObjectId;

public interface WalletsService {

    TransferWalletDto createWallet(ObjectId ownerId, OwnerType ownerType);

    TransferWalletDto getWalletById(ObjectId walletId);

    TransferWalletDto getWalletByOwnerId(ObjectId ownerId);

    boolean isWalletHasEnoughAmountByWalletId(ObjectId walletId, double paymentMoney);

    boolean isWalletHasEnoughAmountByOwnerId(ObjectId ownerId, double paymentMoney);

    TransferWalletDto updateWalletAmount(ObjectId walletId, AmountActionType amountActionType, double amount);

    void deleteWalletById(ObjectId id);

}
