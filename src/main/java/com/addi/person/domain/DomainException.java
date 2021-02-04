package com.addi.person.domain;

public class DomainException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    public DomainException(String message) {
        super(message);
    }
}
