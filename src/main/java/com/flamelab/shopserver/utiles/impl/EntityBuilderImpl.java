package com.flamelab.shopserver.utiles.impl;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.entities.CommonEntity;
import com.flamelab.shopserver.exceptions.EntityDoesNotCreatedException;
import com.flamelab.shopserver.utiles.EntityBuilder;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EntityBuilderImpl<E extends CommonEntity, D extends CommonCreateDto> implements EntityBuilder<E, D> {

    private final ClassUtilityImpl<E> entityClassUtility;
    private final ClassUtilityImpl<D> dtoClassUtility;

    public E buildEntityFromDto(D createDto, Class<D> createDtoClass, Class<E> entityClass) {
        E entity = entityClassUtility.createClassInstance(entityClass);
        setCommonEntityValues(entity);
        List<Field> allEntityFields = entityClassUtility.getAllClassFields(entityClass).stream().peek(f -> f.setAccessible(true)).toList();
        List<Field> allDtoFields = dtoClassUtility.getAllClassFields(createDtoClass).stream().peek(f -> f.setAccessible(true)).toList();
        Map<String, Field> entityFieldsByName = new HashMap<>();
        for (Field entityField : allEntityFields) {
            entityFieldsByName.put(entityField.getName(), entityField);
        }
        try {
            for (Field dtoField : allDtoFields) {
                Field entityField = entityFieldsByName.get(dtoField.getName());
                entityField.set(entity, dtoField.get(createDto));
            }
        } catch (IllegalAccessException e) {
            throw new EntityDoesNotCreatedException(String.format(
                    "Something went wrong during creation '%s'",
                    entity.getClass().getName()));
        }
        entityClassUtility.declineAccessibleToFields(allEntityFields);
        dtoClassUtility.declineAccessibleToFields(allDtoFields);
        return entity;
    }

    private void setCommonEntityValues(E instance) {
        instance.setId(ObjectId.get());
        instance.setCreatedDate(LocalDateTime.now());
        instance.setLastUpdatedDate(LocalDateTime.now());
    }

}
