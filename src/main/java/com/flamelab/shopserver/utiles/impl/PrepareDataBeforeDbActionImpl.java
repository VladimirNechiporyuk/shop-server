package com.flamelab.shopserver.utiles.impl;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.dtos.update.CommonUpdateDto;
import com.flamelab.shopserver.entities.CommonEntity;
import com.flamelab.shopserver.utiles.DifferenceUtility;
import com.flamelab.shopserver.utiles.EntityBuilder;
import com.flamelab.shopserver.utiles.MapperUtility;
import com.flamelab.shopserver.utiles.PrepareDataBeforeDbAction;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PrepareDataBeforeDbActionImpl<E extends CommonEntity, C extends CommonCreateDto, U extends CommonUpdateDto>
        implements PrepareDataBeforeDbAction<E, C, U> {

    private final EntityBuilder<E, C> entityBuilder;
    private final DifferenceUtility<E> differenceUtility;
    private final MapperUtility<U, E> mapperUtilityFromUpdateDtoToEntity;

    @Override
    public E prepareNewEntity(C createDto, Class<C> createDtoClass, Class<E> entityClass) {
        return entityBuilder.buildEntityFromDto(createDto, createDtoClass, entityClass);
    }

    @Override
    public Map<FieldNames, Object> prepareChanges(E entity, U updatedDto, Class<U> updateDtoClass, Class<E> entityClass) {
        E entityWithNewValues = mapperUtilityFromUpdateDtoToEntity.map(updatedDto, updateDtoClass, entityClass);
        return differenceUtility.getChanges(entity, entityWithNewValues, entityClass);
    }

}
