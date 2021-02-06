package com.addi.sales_pipeline.infraestructure.external_systems;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public final class Score {

    private Integer value;

    public Score(Integer value) {
        this.value = value;
    }

    Score() { }

    public Integer getValue() {
        return value;
    }
}
