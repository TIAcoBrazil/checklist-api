package com.fleet.fleet.models.enums;

import lombok.Getter;

@Getter
public enum VehicleSituation {
    BLOCKED('B'),
    AVAILABLE('L');

    private final char situation;

    VehicleSituation(char situation) {this.situation = situation;}
}
