package ru.javawebinar.topjava.repository.jdbc;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class JdbcValidation {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private JdbcValidation() {}

    public static <T> void validate(T obj) {
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
