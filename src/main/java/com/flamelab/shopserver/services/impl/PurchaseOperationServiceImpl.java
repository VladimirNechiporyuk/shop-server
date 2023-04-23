package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.create.CreatePurchaseOperationDto;
import com.flamelab.shopserver.entities.PurchaseOperation;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.mappers.PurchaseOperationMapper;
import com.flamelab.shopserver.repositories.PurchaseOperationsRepository;
import com.flamelab.shopserver.services.PurchaseOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Service
@RequiredArgsConstructor
public class PurchaseOperationServiceImpl implements PurchaseOperationService {

    private final PurchaseOperationsRepository purchaseOperationsRepository;
    private final PurchaseOperationMapper purchaseOperationMapper;

    @Override
    public PurchaseOperation createPurchaseOperation(CreatePurchaseOperationDto createPurchaseOperationDto) {
        return purchaseOperationsRepository.save(purchaseOperationMapper.mapToEntity(createPurchaseOperationDto));
    }

    @Override
    public PurchaseOperation getPurchaseOperationById(String operationId) {
        Optional<PurchaseOperation> optionalPurchaseOperation = purchaseOperationsRepository.findById(operationId);
        if (optionalPurchaseOperation.isPresent()) {
            return optionalPurchaseOperation.get();
        } else {
            throw new ResourceException(NO_CONTENT, String.format("Purchase operation with id '%s' does not exists", operationId));
        }
    }

    @Override
    public List<PurchaseOperation> getAllPurchaseOperationsByUser(String userId) {
        return purchaseOperationsRepository.findAllByCustomerId(userId);
    }

    @Override
    public List<PurchaseOperation> getAllPurchaseOperationsByShop(String shopId) {
        return purchaseOperationsRepository.findAllByMerchantId(shopId);
    }
}
