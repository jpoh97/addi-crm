package com.addi.sales_pipeline.domain.model;

import com.addi.sales_pipeline.domain.exception.DomainException;

public enum Stage {

    LEAD, PROSPECT;

    private static final String ONLY_LEADS_CAN_BE_PROMOTED = "Only leads can be promoted";

    public Stage nextStage() {
        Stage[] values = values();
        if (isLastStage(values.length)) {
            throw new DomainException(ONLY_LEADS_CAN_BE_PROMOTED);
        }
        return values[ordinal() + 1];
    }

    private boolean isLastStage(int stagesCount) {
        return ordinal() == stagesCount - 1;
    }
}
