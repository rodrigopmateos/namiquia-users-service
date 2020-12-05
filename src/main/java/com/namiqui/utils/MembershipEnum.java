package com.namiqui.utils;

import java.util.HashMap;
import java.util.Map;

public enum MembershipEnum {
    NONE(0), FREE(1), PRO(2), PREMIUM(3), DEVELOPER(4), ADMIN(5);

    private Integer value;
    private static Map map = new HashMap<>();

    private MembershipEnum(Integer value){
        this.value = value;
    }

    static {
        for (MembershipEnum membershipType : MembershipEnum.values()) {
            map.put(membershipType.value, membershipType);
        }
    }

    public static MembershipEnum valueOf(Integer membership){
        return (MembershipEnum)map.get(membership);
    }

    public Integer getValue(){
        return value;
    }


}
