package com.addi.person.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Email {

    private static final String INVALID_EMAIL_FORMAT_MESSAGE = "Invalid email format";
    private static final String REGEX_VALIDATE_EMAIL_FORMAT = "^.+@.+\\..+$";

    private final String email;

    Email(String email) {
        validateFormat(email);
        this.email = email;
    }

    private void validateFormat(String email) {
        Pattern pattern = Pattern.compile(REGEX_VALIDATE_EMAIL_FORMAT);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new DomainException(INVALID_EMAIL_FORMAT_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
