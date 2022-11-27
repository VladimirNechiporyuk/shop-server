package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.create.CreateWalletDto;
import com.flamelab.shopserver.dtos.transafer.TransferWalletDto;
import com.flamelab.shopserver.dtos.update.UpdateWalletDto;
import com.flamelab.shopserver.entities.Wallet;
import com.flamelab.shopserver.enums.AmountActionType;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.exceptions.WalletHasNotEnoughMoneyException;
import com.flamelab.shopserver.services.WalletsService;
import com.flamelab.shopserver.utiles.DbEntityUtility;
import com.flamelab.shopserver.utiles.MapperUtility;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.utiles.naming.DbCollectionNames.WALLETS__DB_COLLECTION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.ID__FIELD_APPELLATION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.OWNER_ID__FIELD_APPELLATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class WalletsServiceImpl implements WalletsService {

    private final MapperUtility<Wallet, TransferWalletDto> mapperFromEntityToTransferDto;
    private final MapperUtility<Wallet, UpdateWalletDto> mapperFromEntityToUpdateDto;
    private final DbEntityUtility<Wallet, CreateWalletDto, UpdateWalletDto> dbEntityUtility;

    @Override
    public TransferWalletDto createEntity(CreateWalletDto createEntity) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.saveEntity(createEntity, CreateWalletDto.class, Wallet.class, WALLETS__DB_COLLECTION),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    @Override
    public TransferWalletDto getEntityById(ObjectId entityId) {
        return mapperFromEntityToTransferDto.map(
                fetchWalletById(entityId),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    @Override
    public TransferWalletDto getEntityByCriterias(Map<FieldNames, Object> criterias) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.findOneBy(criterias, Wallet.class, WALLETS__DB_COLLECTION),
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
    public List<TransferWalletDto> getAllEntities() {
        return mapperFromEntityToTransferDto.mapToList(
                dbEntityUtility.findAllByClass(Wallet.class, WALLETS__DB_COLLECTION),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    @Override
    public boolean isEntityExistsByCriterias(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.isEntityExistsBy(criterias, Wallet.class, WALLETS__DB_COLLECTION);
    }

    @Override
    public TransferWalletDto updateWalletAmount(ObjectId walletId, AmountActionType actionType, double amount) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, walletId);
        Wallet wallet = fetchWalletBy(searchCriterias);
        wallet.setAmount(changeAmount(wallet.getAmount(), actionType, amount));
        UpdateWalletDto updateWalletDto = mapperFromEntityToUpdateDto.map(wallet, Wallet.class, UpdateWalletDto.class);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(searchCriterias, updateWalletDto, UpdateWalletDto.class, Wallet.class, WALLETS__DB_COLLECTION),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    private double changeAmount(double walletAmount, AmountActionType actionType, double amount) {
        switch (actionType) {
            case INCREASE -> {
                return walletAmount + amount;
            }
            case DECREASE -> {
                if (walletAmount < amount) {
                    throw new WalletHasNotEnoughMoneyException(String.format("The wallet with id '%s' has less money amount then '%s'", walletAmount, amount));
                }
                return walletAmount - amount;
            }
            default ->
                    throw new ResourceException(BAD_REQUEST, String.format("Wrong AmountActionType provided. Provided action type: '%s'", actionType));
        }
    }

    @Override
    public TransferWalletDto updateEntityById(ObjectId entityId, UpdateWalletDto dtoWithNewData) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(Map.of(ID__FIELD_APPELLATION, entityId), dtoWithNewData, UpdateWalletDto.class, Wallet.class, WALLETS__DB_COLLECTION),
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
    public TransferWalletDto updateEntityBy(Map<FieldNames, Object> criterias, UpdateWalletDto dtoWithNewData) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(criterias, dtoWithNewData, UpdateWalletDto.class, Wallet.class, WALLETS__DB_COLLECTION),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    @Override
    public void deleteEntityById(ObjectId entityId) {
        dbEntityUtility.deleteEntityBy(Map.of(ID__FIELD_APPELLATION, entityId), Wallet.class, WALLETS__DB_COLLECTION);
    }

    @Override
    public void deleteEntityByCriterias(Map<FieldNames, Object> criterias) {
        dbEntityUtility.deleteEntityBy(criterias, Wallet.class, WALLETS__DB_COLLECTION);
    }

    private Wallet fetchWalletById(ObjectId walletId) {
        return dbEntityUtility.findOneBy(Map.of(ID__FIELD_APPELLATION, walletId), Wallet.class, WALLETS__DB_COLLECTION);
    }

    private Wallet fetchWalletBy(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.findOneBy(criterias, Wallet.class, WALLETS__DB_COLLECTION);
    }

}
