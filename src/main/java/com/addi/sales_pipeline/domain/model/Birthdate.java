package com.addi.sales_pipeline.domain.model;

import com.addi.sales_pipeline.domain.exception.DomainException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDate;
import java.util.Objects;

@RegisterForReflection
final class Birthdate {

    private static final String BIRTHDATE_IN_THE_FUTURE_ERROR_MESSAGE = "Birthdate cannot be in the future";
    private static final String BIRTHDATE_MANY_YEARS_AGO_ERROR_MESSAGE = "Birthdate cannot be 150 years ago";
    private static final int MAX_VALID_AGE = 150;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate value;

    Birthdate(LocalDate value) {
        validateDate(value);
        this.value = value;
    }

    Birthdate() { }

    public LocalDate getValue() {
        return value;
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
        return Objects.equals(value, birthdate1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}