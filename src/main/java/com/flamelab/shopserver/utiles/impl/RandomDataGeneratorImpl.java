package com.flamelab.shopserver.utiles.impl;

import com.flamelab.shopserver.utiles.RandomDataGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class RandomDataGeneratorImpl implements RandomDataGenerator {

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String generateAuthToken() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int authTokenLength = 300;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(authTokenLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public int generateTemporaryCode() {
        Random random = new Random();
        return random.nextInt(100000, 999999);
    }

}
