package com.fleet.fleet.models.enums;

import lombok.Getter;

@Getter
public enum ChecklistStatus {
    NEW('N'),
    FINISHED('F'),
    PENDING('P');

    private final char status;

    ChecklistStatus(char status) { this.status = status; }

}
