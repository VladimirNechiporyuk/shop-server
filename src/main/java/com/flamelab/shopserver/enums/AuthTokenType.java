package com.flamelab.shopserver.enums;

public enum AuthTokenType {
    BEARER("Bearer")
    ;

    private final String typeName;

    AuthTokenType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

}
