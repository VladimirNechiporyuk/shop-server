package com.flamelab.shopserver.utiles;

public interface RandomDataGenerator {

    String generateId();

    String generateAuthToken();

    int generateTemporaryCode();

}
