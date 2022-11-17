package com.flamelab.shopserver.utiles;


import com.flamelab.shopserver.entities.CommonEntity;
import com.flamelab.shopserver.utiles.naming.DbCollectionNames;
import com.flamelab.shopserver.utiles.naming.FieldNames;

public interface ValidatorUtility<E extends CommonEntity> {

    void validateValueOnUniqueness(FieldNames key, Object value, Class<E> searchedClass, DbCollectionNames dbCollectionName);

    void validateIsValueCorrect(Object correctValue, Object checkedValue);
}
