package com.flamelab.shopserver.utiles.data;

import java.util.Date;

public class ConstantsProvider {

    public static final String superAdminName = "Super Admin";
    public static final String superAdminEmail = "superadmin@mail.com";
    public static final String superAdminPassword = "Autentification1!";

    public static final Date accessTokenExpiredTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000);// 30 minutes
    public static final Date refreshTokenExpiredTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000);// 30 minutes

}
