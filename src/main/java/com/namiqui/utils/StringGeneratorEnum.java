package com.namiqui.utils;

import java.util.Random;
import java.util.function.Function;

public enum StringGeneratorEnum {
    ALPHANUMERIC((l) ->{
        final String ALPHANUMERIC_CODE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrwtuvwxyz@";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(l);
        for (int i = 0; i < l; i++) {
            sb.append(ALPHANUMERIC_CODE.charAt(rnd.nextInt(ALPHANUMERIC_CODE.length())));
        }
        return sb.toString();
    }),
    DIGITS((l) ->  {
        final String NUMERIC_CODE="0123456789";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(l);
        for (int i = 0; i < l; i++) {
            sb.append(NUMERIC_CODE.charAt(rnd.nextInt(NUMERIC_CODE.length())));
        }
        return sb.toString();
    });

    StringGeneratorEnum(Function<Integer, String> type){
        this.type = type;
    }

    private final Function<Integer, String> type;

    public String apply(Integer len){
        return type.apply(len);
    }

}
