package com.fleet.fleet.exceptions;

public class OpenChecklistExists extends RuntimeException {
    public OpenChecklistExists(String message) {
        super(message);
    }
}
