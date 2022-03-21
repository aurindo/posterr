package com.aurindo.posterr.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphanumericValidator implements
        ConstraintValidator<AlphanumericConstraint, String> {

    private static final String regex = "^[a-zA-Z0-9]+$";
    private static final Pattern pattern = Pattern.compile(regex);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
