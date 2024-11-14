package com.fleet.fleet.exceptions;

public class PendingChecklistException extends RuntimeException {
    public PendingChecklistException(String message) {
        super(message);
    }
}
