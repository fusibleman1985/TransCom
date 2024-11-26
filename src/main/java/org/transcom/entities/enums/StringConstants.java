package org.transcom.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StringConstants {

    UNKNOWN_USER("Unknown user"),
    UNKNOWN_ID("Unknown_ID"),
    BASE_URL("http://localhost:8080/transcom/"),
    PLACEHOLDER_TRUCK_TYPE_IMAGE("images/truck-type-placeholder.png"),
    DEFAULT_URL_TRUCK_TYPE_IMAGE(BASE_URL.value + PLACEHOLDER_TRUCK_TYPE_IMAGE.value);

    private final String value;

}
