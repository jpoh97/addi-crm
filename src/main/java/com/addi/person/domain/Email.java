package com.addi.person.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Email {

    private final String email;

    Email(String email) {
        validateFormat(email);
        this.email = email;
    }

    private void validateFormat(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new DomainException("Invalid email format");
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
