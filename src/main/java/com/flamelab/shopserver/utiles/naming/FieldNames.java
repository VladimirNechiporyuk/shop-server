package com.flamelab.shopserver.utiles.naming;

import com.flamelab.shopserver.exceptions.WrongCriteriaNameProvidedException;
import jdk.jfr.Description;

import java.util.Arrays;
import java.util.List;

@Description("Fields naming is using for correct mapping from one object type to other during mapping and also for actions in data base" +
        "Fields related to classes in packages:" +
        "com.flamelab.gameserver.entities" +
        "com.flamelab.gameserver.dtos.create" +
        "com.flamelab.gameserver.dtos.transfer" +
        "com.flamelab.gameserver.dtos.update")
public enum FieldNames {

    ID__FIELD_APPELLATION("id"),
    CREATED_DATE__FIELD_APPELLATION("createdDate"),
    LAST_UPDATED_DATE__FIELD_APPELLATION("lastUpdatedDate"),
    NAME__FIELD_APPELLATION("name"),
    EMAIL__FIELD_APPELLATION("email"),
    WALLET_ID__FIELD_APPELLATION("walletId"),
    BASKET__FIELD_APPELLATION("basket"),
    OWNER_ID__FIELD_APPELLATION("ownerId"),
    OWNER_TYPE__FIELD_APPELLATION("ownerType"),
    AMOUNT__FIELD_APPELLATION("amount"),
    PRODUCTS__FIELD_APPELLATION("products");

    private final String field;

    FieldNames(String field) {
        this.field = field;
    }

    public String getField() {
        return this.field;
    }

    public static List<FieldNames> asList() {
        return Arrays.stream(FieldNames.values()).toList();
    }

    public static FieldNames getFieldAppellation(String name) {
        if (containsField(name)) {
            return getFieldAppellationByField(name);
        } else if (containsAppellation(name)) {
            return getFieldAppellationByAppellation(name);
        }
        throw new WrongCriteriaNameProvidedException(String.format("There is no field appellation with name: '%s'", name));
    }

    public static FieldNames getFieldAppellationByAppellation(String name) {
        for (FieldNames fieldName : FieldNames.values()) {
            if (fieldName.toString().equals(name)) {
                return fieldName;
            }
        }
        throw new WrongCriteriaNameProvidedException(String.format("There is no field appellation with name: '%s'", name));
    }

    public static FieldNames getFieldAppellationByField(String name) {
        for (FieldNames fieldName : FieldNames.values()) {
            if (fieldName.getField().equals(name)) {
                return fieldName;
            }
        }
        throw new WrongCriteriaNameProvidedException(String.format("There is no field appellation by field: '%s'", name));
    }

    public static boolean containsAppellation(String name) {
        return asList().stream().map(Enum::toString).toList().contains(name);
    }

    public static boolean containsField(String name) {
        for (FieldNames fieldName : FieldNames.values()) {
            if (fieldName.getField().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
