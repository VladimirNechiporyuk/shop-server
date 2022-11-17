package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.transafer.TransferWalletDto;
import com.flamelab.shopserver.entities.Wallet;
import com.flamelab.shopserver.enums.AmountActionType;
import com.flamelab.shopserver.enums.OwnerType;
import com.flamelab.shopserver.exceptions.WalletHasNotEnoughMoneyException;
import com.flamelab.shopserver.services.WalletsService;
import com.flamelab.shopserver.utiles.DbEntityUtility;
import com.flamelab.shopserver.utiles.MapperUtility;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.flamelab.shopserver.utiles.naming.DbCollectionNames.WALLETS__DB_COLLECTION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.*;

@Service
@RequiredArgsConstructor
public class WalletsServiceImpl implements WalletsService {

    private final MapperUtility<Wallet, TransferWalletDto> mapperFromEntityToTransferDto;
    private final DbEntityUtility<Wallet> dbEntityUtility;

    @Override
    public TransferWalletDto createWallet(ObjectId ownerId, OwnerType ownerType) {
        Wallet wallet = new Wallet(ownerId, ownerType, 0);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.saveEntity(wallet, Wallet.class, WALLETS__DB_COLLECTION),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    @Override
    public TransferWalletDto getWalletById(ObjectId walletId) {
        return mapperFromEntityToTransferDto.map(
                fetchWalletById(walletId),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    @Override
    public TransferWalletDto getWalletByOwnerId(ObjectId ownerId) {
        return mapperFromEntityToTransferDto.map(
                fetchWalletBy(Map.of(OWNER_ID__FIELD_APPELLATION, ownerId)),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    @Override
    public boolean isWalletHasEnoughAmountByWalletId(ObjectId walletId, double paymentMoney) {
        Wallet wallet = fetchWalletById(walletId);
        return wallet.getAmount() >= paymentMoney;
    }

    @Override
    public boolean isWalletHasEnoughAmountByOwnerId(ObjectId ownerId, double paymentMoney) {
        Wallet wallet = fetchWalletBy(Map.of(OWNER_ID__FIELD_APPELLATION, ownerId));
        return wallet.getAmount() >= paymentMoney;
    }

    @Override
    public TransferWalletDto updateWalletAmount(ObjectId walletId, AmountActionType amountActionType, double amount) {
        Wallet wallet = fetchWalletBy(Map.of(ID__FIELD_APPELLATION, walletId));
        double resultAmount = increaseOrDecreaseWalletAmount(wallet.getAmount(), amountActionType, amount);
        if (resultAmount < 0.0) {
            throw new WalletHasNotEnoughMoneyException(String.format("The wallet with id '%s' has not enough money for making the payment", walletId));
        }
        Map<FieldNames, Object> changes = Map.of(AMOUNT__FIELD_APPELLATION, resultAmount);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(wallet, Wallet.class, changes, WALLETS__DB_COLLECTION),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    @Override
    public void deleteWalletById(ObjectId walletId) {
        dbEntityUtility.deleteEntityBy(Map.of(ID__FIELD_APPELLATION, walletId), Wallet.class, WALLETS__DB_COLLECTION);
    }

    private Wallet fetchWalletById(ObjectId walletId) {
        return dbEntityUtility.findOneBy(Map.of(ID__FIELD_APPELLATION, walletId), Wallet.class, WALLETS__DB_COLLECTION);
    }

    private Wallet fetchWalletBy(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.findOneBy(criterias, Wallet.class, WALLETS__DB_COLLECTION);
    }

    private double increaseOrDecreaseWalletAmount(double walletAmount, AmountActionType amountActionType, double amountDelta) {
        switch (amountActionType) {
            case INCREASE -> {
                return walletAmount + amountDelta;
            }
            case DECREASE -> {
                return walletAmount - amountDelta;
            }
            default -> throw new RuntimeException("Incorrect amountActionType value provided");
        }
    }

}
