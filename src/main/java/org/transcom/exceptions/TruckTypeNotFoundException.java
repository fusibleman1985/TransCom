package org.transcom.exceptions;

public class TruckTypeNotFoundException extends RuntimeException {
    public TruckTypeNotFoundException(String message) {
        super(message);
    }
}
