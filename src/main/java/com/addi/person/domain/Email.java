package com.addi.person.domain;

import java.util.Objects;

final class Email {

    private final String email;

    static Email create(String emailText) {
        return new Email(emailText);
    }

    private Email(String email) {
        this.email = email;
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
