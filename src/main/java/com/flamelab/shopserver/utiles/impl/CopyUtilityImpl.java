package com.flamelab.shopserver.utiles.impl;

import com.flamelab.shopserver.exceptions.NoFiledFoundDuringMappingException;
import com.flamelab.shopserver.utiles.CopyUtility;
import com.flamelab.shopserver.utiles.data.ObjectWithData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CopyUtilityImpl<C extends ObjectWithData> implements CopyUtility<C> {

    private final ClassUtilityImpl<C> classUtility;

    public C copy(C original, Class<C> copiedClass) {
        C instance = classUtility.createClassInstance(copiedClass);
        List<Field> fields = classUtility.getAllClassFields(copiedClass);
        Map<String, Object> originalFieldsWithValues = classUtility.getFieldsWithValues(original, copiedClass);
        for (Field field : fields) {
            try {
                setValueToField(instance, originalFieldsWithValues, field);
            } catch (IllegalAccessException e) {
                throw new NoFiledFoundDuringMappingException(String.format("No field with name %s found in dto class during copying", field.getName()));
            }
        }
        return instance;
    }

    private void setValueToField(Object newInstance, Map<String, Object> originalFieldsWithValues, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(newInstance, originalFieldsWithValues.get(field.getName()));
    }

}
