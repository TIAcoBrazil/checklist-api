package com.fleet.fleet.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnswerStatus {
    RESOLVED(1),
    UNRESOLVED(0);

    private final int value;
}
