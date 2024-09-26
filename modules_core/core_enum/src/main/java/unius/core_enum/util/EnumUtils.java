package unius.core_enum.util;

public class EnumUtils {

    private EnumUtils() {}

    public static <E extends Enum<E>> boolean isExistValue(Class<E> enumClass, String value) {
        if(value == null || enumClass == null) return false;

        try {
            Enum.valueOf(enumClass, value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static <E extends Enum<E>> E getEnumValue(Class<E> enumClass, String value) {
        if(value == null || enumClass == null) throw new RuntimeException(); // TODO Exception 설정

        for(E constant : enumClass.getEnumConstants()) {
            if(constant.name().equalsIgnoreCase(value)) {
                return constant;
            }
        }

        throw new IllegalArgumentException("Invalid value for enum: " + value);
    }
}