package com.fleet.fleet.exceptions;

public class OpenChecklistExistsException extends RuntimeException {
    public OpenChecklistExistsException(String message) {
        super(message);
    }
}
