package com.flamelab.shopserver.utiles.naming;

import com.flamelab.shopserver.exceptions.WrongCriteriaNameProvidedException;

import java.util.HashMap;
import java.util.Map;

import static com.flamelab.shopserver.utiles.naming.FieldNames.containsField;
import static com.flamelab.shopserver.utiles.naming.FieldNames.getFieldAppellation;

public class FieldMapper {

    public static Map<FieldNames, Object> mapCriterias(Map<String, Object> criterias) {
        Map<FieldNames, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : criterias.entrySet()) {
            if (!containsField(entry.getKey())) {
                throw new WrongCriteriaNameProvidedException(String.format("Provided criteria does not exist '%s'", entry.getKey()));
            }
            result.put(getFieldAppellation(entry.getKey()), entry.getValue());
        }
        return result;
    }

}
