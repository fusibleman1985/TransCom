package org.transcom.exceptions;

public class TruckNotFoundException extends RuntimeException {
    public TruckNotFoundException(String message) {
        super(message);
    }
}
