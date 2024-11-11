package com.fleet.fleet.models.enums;

import lombok.Getter;

@Getter
public enum Risk {
    LOW("L"),
    MODERATE("M"),
    CRITICAL("C");

    private final String risk;

    Risk(String risk) {this.risk = risk;}
}
