package com.lister.esb.utils;


import com.lister.esb.enums.SourceSystem;

public class EntityEnumHelper {

    public static <T> T find(Class<T> enumClass, String code) {
        T match = null;
        for (T enumType : enumClass.getEnumConstants()) {
            if (enumClass.equals(enumClass) && ((SourceSystem) enumType).getCode().equalsIgnoreCase(code)) {
                match = enumType;
                break;
            }
        }
        return match;
    }
}
