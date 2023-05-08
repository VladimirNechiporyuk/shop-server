package com.flamelab.shopserver.enums;

import java.util.Collections;
import java.util.List;

public enum Roles {

    ADMIN, CUSTOMER, MERCHANT;

    public static List<Roles> ADMIN() {
        return Collections.singletonList(ADMIN);
    }

    public static List<Roles> CUSTOMER() {
        return Collections.singletonList(CUSTOMER);
    }

    public static List<Roles> MERCHANT() {
        return Collections.singletonList(MERCHANT);
    }

    public static List<Roles> ADMIN_CUSTOMER() {
        return List.of(ADMIN, CUSTOMER);
    }

    public static List<Roles> ADMIN_MERCHANT() {
        return List.of(ADMIN, MERCHANT);
    }

    public static List<Roles> MERCHANT_CUSTOMER() {
        return List.of(MERCHANT, CUSTOMER);
    }

    public static List<Roles> ADMIN_CUSTOMER_MERCHANT() {
        return List.of(ADMIN, CUSTOMER, MERCHANT);
    }

}
