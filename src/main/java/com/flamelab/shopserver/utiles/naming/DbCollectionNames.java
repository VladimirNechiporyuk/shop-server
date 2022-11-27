package com.flamelab.shopserver.utiles.naming;

public enum DbCollectionNames {

    SHOPS__DB_COLLECTION("shops"),
    USERS__DB_COLLECTION("users"),
    WALLETS__DB_COLLECTION("wallets");

    private final String collection;

    DbCollectionNames(String collection) {
        this.collection = collection;
    }

    public String getCollection() {
        return this.collection;
    }
}
