package com.banry.pscm.service.util;

import com.banry.pscm.service.enums.CodeEnum;

/**
 * xudk
 * 2018-10-26
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
