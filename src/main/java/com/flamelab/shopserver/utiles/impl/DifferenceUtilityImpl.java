package com.flamelab.shopserver.utiles.impl;

import com.flamelab.shopserver.exceptions.DifferentClassesException;
import com.flamelab.shopserver.exceptions.NoDifferenceBetweenObjectsException;
import com.flamelab.shopserver.utiles.ClassUtility;
import com.flamelab.shopserver.utiles.DifferenceUtility;
import com.flamelab.shopserver.utiles.data.ObjectWithData;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DifferenceUtilityImpl<T extends ObjectWithData> implements DifferenceUtility<T> {

    private final ClassUtility<T> classUtility;

    public Map<FieldNames, Object> getChanges(T objectBeforeChanges, T updatedObject, Class<T> targetClass) {
        if (!objectBeforeChanges.getClass().equals(updatedObject.getClass())) {
            throw new DifferentClassesException(String.format("Classes are difference. existentObject: %s, updatedObject: %s",
                    objectBeforeChanges.getClass().toGenericString(), updatedObject.getClass().toGenericString()));
        }
        return getChangedData(objectBeforeChanges, updatedObject, targetClass);
    }

    private Map<FieldNames, Object> getChangedData(T objectBeforeChanges, T updatedObject, Class<T> targetClass) {
        List<String> fieldsNames = classUtility.getAllClassFields(targetClass).stream().map(Field::getName).toList();
        Map<String, Object> objectBeforeChangesFieldsWithValues = classUtility.getFieldsWithValues(objectBeforeChanges, targetClass);
        Map<String, Object> updatedObjectFieldsWithValues = classUtility.getFieldsWithValues(updatedObject, targetClass);
        Map<FieldNames, Object> changes = new HashMap<>();
        for (String fieldName : fieldsNames) {
            if (objectBeforeChangesFieldsWithValues.containsKey(fieldName)
                    && updatedObjectFieldsWithValues.containsKey(fieldName)
                    && updatedObjectFieldsWithValues.get(fieldName) != null
                    && !objectBeforeChangesFieldsWithValues.get(fieldName).equals(updatedObjectFieldsWithValues.get(fieldName))) {
                FieldNames fieldNameAppellation = FieldNames.getFieldAppellationByName(fieldName);
                changes.put(fieldNameAppellation, updatedObjectFieldsWithValues.get(fieldName));
            }
        }
        validateIsObjectsHasChanges(changes, targetClass, updatedObject);
        return changes;
    }

    private void validateIsObjectsHasChanges(Map<FieldNames, Object> fieldsWithUpdatedValues, Object objectBeforeChanges, Object updatedObject) {
        if (fieldsWithUpdatedValues.isEmpty()) {
            throw new NoDifferenceBetweenObjectsException(String.format(
                    """
                            Objects has no difference. \\
                            Object before changes: %s \\
                            Object with updates: %s""",
                    objectBeforeChanges.toString(), updatedObject.toString()));
        }
    }

}
