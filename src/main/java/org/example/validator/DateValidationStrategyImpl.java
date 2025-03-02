package org.example.validator;

import java.util.regex.Pattern;

public final class DateValidationStrategyImpl implements ValidationStrategy<String> {

    public static String pattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    @Override
    public boolean isValid(String s) {
        return Pattern.matches(pattern, s);
    }
}
