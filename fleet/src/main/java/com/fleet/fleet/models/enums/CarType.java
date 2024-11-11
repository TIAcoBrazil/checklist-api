package com.fleet.fleet.models.enums;

import lombok.Getter;

@Getter
public enum CarType {

    LIGHT("L"),
    MEDIUM("M"),
    HEAVY("P"),
    EXTRAHEAVY("E");

    private final String carType;

    CarType(String carType) {
        this.carType = carType;
    }

}
