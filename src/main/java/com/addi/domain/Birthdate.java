package com.addi.domain;

import java.time.LocalDate;
import java.util.Objects;

final class Birthdate {

    private final LocalDate birthdate;

    static Birthdate create(LocalDate birthdate) {
        return new Birthdate(birthdate);
    }

    private Birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Birthdate birthdate1 = (Birthdate) o;
        return Objects.equals(birthdate, birthdate1.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthdate);
    }
}
