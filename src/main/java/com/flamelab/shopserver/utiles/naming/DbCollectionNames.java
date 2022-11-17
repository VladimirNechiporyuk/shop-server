package com.flamelab.shopserver.utiles.naming;

public enum DbCollectionNames {

    SHOPS__DB_COLLECTION("shops"),
    USERS__DB_COLLECTION("users"),
    WALLETS__DB_COLLECTION("wallets");

    private final String name;

    DbCollectionNames(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
