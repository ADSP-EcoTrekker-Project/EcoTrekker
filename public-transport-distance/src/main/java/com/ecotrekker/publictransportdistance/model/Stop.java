package com.ecotrekker.publictransportdistance.model;

import lombok.Data;

@Data
public class Stop {
    private String stopId;
    private String stopName;
    private String stopLat;
    private String stopLon;
    private Double distanceToNextStopKm;

    @Override
    public int hashCode() {
        return stopId.hashCode();
    } 
}