package com.addi.person.domain;

import java.time.LocalDate;
import java.util.Objects;

final class Birthdate {

    private static final String BIRTHDATE_IN_THE_FUTURE_ERROR_MESSAGE = "Birthdate cannot be in the future";
    private static final String BIRTHDATE_MANY_YEARS_AGO_ERROR_MESSAGE = "Birthdate cannot be 150 years ago";
    private static final int MAX_VALID_AGE = 150;

    private final LocalDate birthdate;

    Birthdate(LocalDate birthdate) {
        validateDate(birthdate);
        this.birthdate = birthdate;
    }

    private void validateDate(LocalDate birthdate) {
        if (isInTheFuture(birthdate)) {
            throw new DomainException(BIRTHDATE_IN_THE_FUTURE_ERROR_MESSAGE);
        }
        if (isMaxAge(birthdate)) {
            throw new DomainException(BIRTHDATE_MANY_YEARS_AGO_ERROR_MESSAGE);
        }
    }

    private boolean isInTheFuture(LocalDate birthdate) {
        return birthdate.isAfter(LocalDate.now());
    }

    private boolean isMaxAge(LocalDate birthdate) {
        return birthdate.isBefore(LocalDate.now().minusYears(MAX_VALID_AGE));
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