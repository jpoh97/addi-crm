package com.addi.domain;

final class Email {

    private final String email;

    static Email create(String emailText) {
        return new Email(emailText);
    }

    private Email(String email) {
        this.email = email;
    }
}
