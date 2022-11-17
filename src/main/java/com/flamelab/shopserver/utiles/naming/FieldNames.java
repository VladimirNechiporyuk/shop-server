package com.flamelab.shopserver.utiles.naming;

import jdk.jfr.Description;

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
    WALLET_ID__FIELD_APPELLATION("walletId"),
    BASKET__FIELD_APPELLATION("basket"),
    OWNER_ID__FIELD_APPELLATION("ownerId"),
    OWNER_TYPE__FIELD_APPELLATION("ownerType"),
    AMOUNT__FIELD_APPELLATION("amount"),
    PRODUCTS__FIELD_APPELLATION("products"),

    ;

    private final String field;

    FieldNames(String field) {
        this.field = field;
    }

    public String getField() {
        return this.field;
    }

    public static FieldNames getFieldAppellationByName(String name) {
        switch (name) {
            case "id" -> {
                return ID__FIELD_APPELLATION;
            }
            case "createdDate" -> {
                return CREATED_DATE__FIELD_APPELLATION;
            }
            case "lastUpdatedDate" -> {
                return LAST_UPDATED_DATE__FIELD_APPELLATION;
            }
            case "name" -> {
                return NAME__FIELD_APPELLATION;
            }
            default -> throw new RuntimeException(String.format("There is no field with name: %s", name));
        }
    }
}
