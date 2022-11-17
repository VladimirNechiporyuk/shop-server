package com.flamelab.shopserver.utiles.impl;

import com.flamelab.shopserver.entities.CommonEntity;
import com.flamelab.shopserver.exceptions.EntityAlreadyExistsException;
import com.flamelab.shopserver.utiles.ValidatorUtility;
import com.flamelab.shopserver.utiles.naming.DbCollectionNames;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ValidatorUtilityImpl<E extends CommonEntity> implements ValidatorUtility<E> {

    private final MongoDbEntityUtilityImpl<E> dbEntityUtility;

    public void validateValueOnUniqueness(FieldNames parameter, Object value, Class<E> searchedClass, DbCollectionNames dbCollectionName) {
        if (dbEntityUtility.isEntityExistsBy(Map.of(parameter, value), searchedClass, dbCollectionName)) {
            throw new EntityAlreadyExistsException(String.format("Entity with parameter '%s': '%s' already exists in collection '%s'",
                    parameter, value, dbCollectionName));
        }
    }

    @Override
    public void validateIsValueCorrect(Object correctValue, Object checkedValue) {
        if (!checkedValue.equals(correctValue)) {
            throw new RuntimeException(String.format("Value '%s' should be equals to '%s'", checkedValue, correctValue));
        }
    }

}
