package com.fleet.fleet.models.enums;

import lombok.Getter;

@Getter
public enum Route {
    START("I"),
    END("F");

    private final String route;

    Route(String route) { this.route = route; }
}
