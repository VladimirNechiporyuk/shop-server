package com.flamelab.shopserver.utiles.impl;

import com.flamelab.shopserver.utiles.MapperUtility;
import com.flamelab.shopserver.utiles.data.ObjectWithData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MapperUtilityImpl<F extends ObjectWithData, T extends ObjectWithData> implements MapperUtility<F, T> {

    private final ClassUtilityImpl<F> fromClassUtility;
    private final ClassUtilityImpl<T> toClassUtility;

    public T map(F from, Class<F> fromClass, Class<T> toClass) {
        T toClassInstance = toClassUtility.createClassInstance(toClass);
        Map<String, Object> fromClassFieldsWithValues = fromClassUtility.getFieldsWithValues(from, fromClass);
        List<Field> toClassFields = toClassUtility.getAllClassFields(toClass).stream().peek(f -> f.setAccessible(true)).toList();
        for (Field toClassField : toClassFields) {
            if (fromClassFieldsWithValues.containsKey(toClassField.getName())) {
                try {
                    toClassField.set(toClassInstance, fromClassFieldsWithValues.get(toClassField.getName()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        toClassUtility.declineAccessibleToFields(toClassFields);
        return toClassInstance;
    }

    public List<T> mapToList(List<F> from, Class<F> fromClass, Class<T> toClass) {
        return from.stream()
                .map(o -> map(o, fromClass, toClass))
                .collect(Collectors.toList());
    }

}
