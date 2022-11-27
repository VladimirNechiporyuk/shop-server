package com.flamelab.shopserver.utiles;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.dtos.update.CommonUpdateDto;
import com.flamelab.shopserver.entities.CommonEntity;
import com.flamelab.shopserver.utiles.naming.FieldNames;

import java.util.Map;

public interface PrepareDataBeforeDbAction<E extends CommonEntity, C extends CommonCreateDto, U extends CommonUpdateDto> {

    E prepareNewEntity(C createDto, Class<C> createDtoClass, Class<E> entityClass);

    Map<FieldNames, Object> prepareChanges(E entity, U updatedDto, Class<U> updateDtoClass, Class<E> entityClass);

}
