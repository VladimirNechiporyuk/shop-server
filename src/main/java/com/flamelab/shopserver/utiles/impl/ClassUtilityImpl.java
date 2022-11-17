package com.flamelab.shopserver.utiles.impl;

import com.flamelab.shopserver.utiles.ClassUtility;
import com.flamelab.shopserver.utiles.data.ObjectWithData;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ClassUtilityImpl<T extends ObjectWithData> implements ClassUtility<T> {

    private final String separateSymbol = ",";
    private final char firstArraySymbol = '[';
    private final char lastArraySymbol = ']';

    @Override
    public T createClassInstance(Class<T> targetClass) {
        T instance = null;
        try {
            instance = targetClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
        return instance;
    }

    @Override
    public Field findFieldFromClass(FieldNames requiredFieldName, Class<T> targetClass) {
        Field requiredField = null;
        List<Field> fields = getAllClassFields(targetClass);
        for (Field field : fields) {
            if (field.getName().equals(requiredFieldName.name())) {
                requiredField = field;
                break;
            }
        }
        return requiredField;
    }

    @Override
    public Map<String, Object> getFieldsWithValues(T instance, Class<T> targetClass) {
        List<Field> fields = getAllClassFields(targetClass).stream().peek(f -> f.setAccessible(true)).toList();
        Map<String, Object> fieldsWithValues = new HashMap<>();
        for (Field field : fields) {
            try {
                if (field.get(instance) != null) {
                    fieldsWithValues.put(field.getName(), field.get(instance));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        declineAccessibleToFields(fields);
        return fieldsWithValues;
    }

    @Override
    public boolean isFieldValueEqualsToCriteria(Field field, String criteriaValue, Object objectFromCollection) {
        try {
            field.setAccessible(true);
            String valueFromField = String.valueOf(field.get(objectFromCollection));
            if (isParameterAsList(valueFromField)) {
                valueFromField = valueFromField.substring(1, valueFromField.length() - 1);
                List<String> splicedValues = Arrays.asList(valueFromField.split(separateSymbol));
                splicedValues = splicedValues.stream()
                        .map(String::trim)
                        .collect(Collectors.toList());
                for (String value : splicedValues) {
                    if (value.equals(criteriaValue)) {
                        return true;
                    }
                }
                return false;
            } else {
                return valueFromField.equals(criteriaValue);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Can't get value from field '%s' by object '%s'", field, objectFromCollection));
        }
    }

    @Override
    public boolean isParameterAsList(Object value) {
        String stringValue = String.valueOf(value);
        return stringValue.charAt(0) == firstArraySymbol && stringValue.charAt(stringValue.length() - 1) == lastArraySymbol;
    }

    @Override
    public List<String> getParameterNames(Class entityClass) {
        return getAllClassFields(entityClass)
                .stream()
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<Field> getAllClassFields(Class targetClass) {
        List<Class> classes = new ArrayList<>();
        classes.add(targetClass);
        findAllClassesList(classes, targetClass);
        List<Field> fields = new ArrayList<>();
        for (Class aClass : classes) {
            fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
        }
        return fields;
    }

    @Override
    public T setValuesForFields(T instance, Class<T> targetClass, Map<FieldNames, Object> fieldsWithValues) {
        List<Field> fields = getAllClassFields(targetClass).stream().peek(f -> f.setAccessible(true)).toList();
        Map<String, Object> fieldsStringsWithValues = fieldsWithValues.entrySet()
                .stream()
                .collect(Collectors.toMap(fieldName -> fieldName.getKey().getField(), Map.Entry::getValue));
        for (Field field : fields) {
            try {
                if (fieldsStringsWithValues.containsKey(field.getName())) {
                    field.set(instance, fieldsStringsWithValues.get(field.getName()));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        declineAccessibleToFields(fields);
        return instance;
    }

    @Override
    public void declineAccessibleToFields(List<Field> fields) {
        for (Field field : fields) {
            field.setAccessible(false);
        }
    }

    private void findAllClassesList(List<Class> classes, Class targetClass) {
        if (targetClass.getSuperclass() == Object.class) {
            return;
        }
        Class superClass = targetClass.getSuperclass();
        classes.add(superClass);
        findAllClassesList(classes, superClass);
    }

}
