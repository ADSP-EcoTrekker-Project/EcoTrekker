package com.ecotrekker.co2calculator.model;

public class ConsumptionRequestBuilder {
    private String vehicle;

    public ConsumptionRequestBuilder setVehicle(String vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public ConsumptionRequest build() {
        ConsumptionRequest request = new ConsumptionRequest();
        request.setVehicle_name(vehicle);
        return request;
    }
}
