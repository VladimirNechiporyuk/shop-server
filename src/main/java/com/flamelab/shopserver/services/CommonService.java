package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.dtos.transafer.CommonTransferDto;
import com.flamelab.shopserver.dtos.update.CommonUpdateDto;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface CommonService<C extends CommonCreateDto, T extends CommonTransferDto, U extends CommonUpdateDto> {

    T createEntity(C createEntity);

    T getEntityById(ObjectId entityId);

    T getEntityByCriterias(Map<FieldNames, Object> criterias);

    List<T> getAllEntitiesByCriterias(Map<FieldNames, Object> criterias);

    List<T> getAllEntities();

    boolean isEntityExistsByCriterias(Map<FieldNames, Object> criterias);

    T updateEntityById(ObjectId entityId, U dtoWithNewData);

    T updateEntityBy(Map<FieldNames, Object> criterias, U dtoWithNewData);

    void deleteEntityById(ObjectId entityId);

    void deleteEntityByCriterias(Map<FieldNames, Object> criterias);

}
