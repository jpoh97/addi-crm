package com.addi.domain;

import java.time.LocalDate;

final class Birthdate {

    private final LocalDate birthdate;

    static Birthdate create(LocalDate birthdate) {
        return new Birthdate(birthdate);
    }

    private Birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}
