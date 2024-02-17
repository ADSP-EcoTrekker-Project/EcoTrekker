package com.ecotrekker.gamification.model;

import java.util.List;

import lombok.Data;

@Data
public class GamificationRequest {
    private List<Route> routes;
    private boolean gamification = true;
}
