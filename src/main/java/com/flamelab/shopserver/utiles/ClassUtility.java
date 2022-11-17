package com.flamelab.shopserver.utiles;

import com.flamelab.shopserver.utiles.data.ObjectWithData;
import com.flamelab.shopserver.utiles.naming.FieldNames;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public interface ClassUtility<T extends ObjectWithData> {

    T createClassInstance(Class<T> targetClass);

    Field findFieldFromClass(FieldNames requiredFieldName, Class<T> targetClass);

    Map<String, Object> getFieldsWithValues(T object, Class<T> targetClass);

    boolean isFieldValueEqualsToCriteria(Field field, String criteriaValue, Object objectFromCollection);

    boolean isParameterAsList(Object value);

    List<String> getParameterNames(Class entityClass);

    List<Field> getAllClassFields(Class targetClass);

    T setValuesForFields(T instance, Class<T> targetClass, Map<FieldNames, Object> fieldsWithValues);

    void declineAccessibleToFields(List<Field> fields);

}
