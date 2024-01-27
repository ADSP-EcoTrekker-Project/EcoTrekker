package com.ecotrekker.routemanager.model;

public class RouteServiceException extends Exception {
    public RouteServiceException(String message) {
        super(message);
    }

    public RouteServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
