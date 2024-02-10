package com.ecotrekker.gamification.model;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Route {
    private List<Step> steps;
    private UUID id;
    private Double co2;
    private Double points;
}
