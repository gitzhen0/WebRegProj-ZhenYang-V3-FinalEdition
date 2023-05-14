package com.beaconfire.exception;

public class CustomSomethingNotFoundException extends RuntimeException {
    public CustomSomethingNotFoundException(String message) {
        super(message);
    }
}
