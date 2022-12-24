package de.welikecoding.misc.customskulls.utils;

public class EnumUtil {

    public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String enumName) {
        try {
            Enum.valueOf(enumClass, enumName);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

}
