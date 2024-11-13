package org.transcom.exceptions.enums;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    ORDER_NOT_FOUND("Order not found"),
    TRUCK_NOT_FOUND("Truck not found"),
    ERROR_DELETING_TRUCK_TYPE("There are Trucks of this Truck Type");


    private String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
