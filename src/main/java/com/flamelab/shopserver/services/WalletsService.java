package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.external.CreateWalletDto;
import com.flamelab.shopserver.dtos.update.UpdateWalletDto;
import com.flamelab.shopserver.entities.Wallet;
import com.flamelab.shopserver.enums.AmountActionType;
import org.bson.types.ObjectId;

public interface WalletsService extends CommonService<CreateWalletDto, Wallet, UpdateWalletDto> {

    Wallet getWalletByOwnerId(ObjectId ownerId);

    Wallet updateWalletAmount(ObjectId walletId, AmountActionType actionType, double amount);

    boolean isWalletHasEnoughAmountByWalletId(ObjectId walletId, double paymentMoney);

    boolean isWalletHasEnoughAmountByOwnerId(ObjectId ownerId, double paymentMoney);

}
