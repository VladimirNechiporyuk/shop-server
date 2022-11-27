package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CreateWalletDto;
import com.flamelab.shopserver.dtos.transafer.TransferWalletDto;
import com.flamelab.shopserver.dtos.update.UpdateWalletDto;
import com.flamelab.shopserver.enums.AmountActionType;
import org.bson.types.ObjectId;

public interface WalletsService extends CommonService<CreateWalletDto, TransferWalletDto, UpdateWalletDto> {

    TransferWalletDto getWalletByOwnerId(ObjectId ownerId);

    TransferWalletDto updateWalletAmount(ObjectId walletId, AmountActionType actionType, double amount);

    boolean isWalletHasEnoughAmountByWalletId(ObjectId walletId, double paymentMoney);

    boolean isWalletHasEnoughAmountByOwnerId(ObjectId ownerId, double paymentMoney);

}
