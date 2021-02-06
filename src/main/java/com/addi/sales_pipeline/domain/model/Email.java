package com.addi.sales_pipeline.domain.model;

import com.addi.sales_pipeline.domain.exception.DomainException;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Objects;
import java.util.regex.Pattern;

@RegisterForReflection
final class Email {

    private static final String INVALID_EMAIL_FORMAT_MESSAGE = "Invalid email format";
    private static final String REGEX_VALIDATE_EMAIL_FORMAT = "^.+@.+\\..+$";

    private String value;

    Email(String value) {
        validateFormat(value);
        this.value = value;
    }

    Email() { }

    public String getValue() {
        return value;
    }

    private void validateFormat(String email) {
        if (isInvalidFormat(email)) {
            throw new DomainException(INVALID_EMAIL_FORMAT_MESSAGE);
        }
    }

    private boolean isInvalidFormat(String email) {
        var pattern = Pattern.compile(REGEX_VALIDATE_EMAIL_FORMAT);
        var matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(value, email1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
