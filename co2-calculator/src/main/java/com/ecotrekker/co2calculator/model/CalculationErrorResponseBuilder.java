package com.ecotrekker.co2calculator.model;

public class CalculationErrorResponseBuilder {
    private String errorMessage;

    public CalculationErrorResponseBuilder setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public CalculationErrorResponse build() {
        CalculationErrorResponse response = new CalculationErrorResponse();
        response.setError(errorMessage);
        return response;
    }
}
