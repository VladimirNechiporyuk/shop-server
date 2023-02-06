package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.dtos.update.CommonUpdateDto;
import com.flamelab.shopserver.entities.CommonEntity;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface CommonService<C extends CommonCreateDto, E extends CommonEntity, U extends CommonUpdateDto> {

    E createEntity(C createEntity);

    E getEntityById(ObjectId entityId);

    E getEntityByCriterias(Map<FieldNames, Object> criterias);

    List<E> getAllEntitiesByCriterias(Map<FieldNames, Object> criterias);

    List<E> getAllEntities();

    boolean isEntityExistsByCriterias(Map<FieldNames, Object> criterias);

    E updateEntityById(ObjectId entityId, U dtoWithNewData);

    E updateEntityBy(Map<FieldNames, Object> criterias, U dtoWithNewData);

    void deleteEntityById(ObjectId entityId);

    void deleteEntityByCriterias(Map<FieldNames, Object> criterias);

}
