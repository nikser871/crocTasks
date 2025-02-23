package org.example.utils;

import java.util.regex.Pattern;

/**
 * Валидатор даты
 */
final public class DateValidatorUtil {

    public static String pattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    public static boolean isValidDate(String date) {
        return Pattern.matches(pattern, date);
    }
}
