package com.flamelab.shopserver.utiles.impl;

import com.flamelab.shopserver.utiles.EmailTextProvider;
import org.springframework.stereotype.Service;

@Service
public class EmailTextProviderImpl implements EmailTextProvider {

    private final String serviceUrl = "http://localhost:8052/shop-server/api";

    @Override
    public String provideConfirmRegistrationText(String userId, int tempCode) {
        String confirmRegistrationApiLink = "/users/confirmRegistration";
        return String.format("Registration confirmation link: %s%s/%s/%d", serviceUrl, confirmRegistrationApiLink, userId, tempCode);
    }

    @Override
    public String providePasswordRecoverySendTempCodeText(int tempCode) {
        String tempCodeVerifyApiLink = "/tempCode/verify";
        return String.format("Verification recovery password temporary code link: %s%s/%d", serviceUrl, tempCodeVerifyApiLink, tempCode);
    }

}
