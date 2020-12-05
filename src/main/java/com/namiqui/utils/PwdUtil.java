package com.namiqui.utils;

import java.util.Random;

/**
 * Class password utils
 */
public class PwdUtil {

    /**
     * Generate password random
     * @param len length of password
     * @return password generated
     */
    public static String alphaNumericString(int len, int type_code) {
        final String ALPHANUMERIC_CODE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrwtuvwxyz@";
        final String NUMERIC_CODE="0123456789";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            if(type_code == 1) {
                sb.append(ALPHANUMERIC_CODE.charAt(rnd.nextInt(ALPHANUMERIC_CODE.length())));
            } else {
                sb.append(NUMERIC_CODE.charAt(rnd.nextInt(NUMERIC_CODE.length())));
            }
        }
        return sb.toString();
    }

}
