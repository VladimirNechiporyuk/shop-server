package com.flamelab.shopserver.utiles;

public interface EmailTextProvider {

    String provideConfirmRegistrationText(String userId, int tempCode);

    String providePasswordRecoverySendTempCodeText(int tempCode);

}
