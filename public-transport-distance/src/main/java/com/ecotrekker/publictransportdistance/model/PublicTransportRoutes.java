package com.ecotrekker.publictransportdistance.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PublicTransportRoutes {
    private Map<String, VehicleRoute> vehicles;

    @Data
    public static class VehicleRoute {
        private String lineName;
        private String lineProduct;
        private List<String> routeIds;
        private List<Route> routes;


        @Data
        public static class Route {
            private String routeId;
            private String routeStart;
            private String routeEnd;
            private List<Stop> stops;


            @Data
            public static class Stop {
                private String stopId;
                private String stopName;
                private String stopLat;
                private String stopLon;
                private Double distanceToNextStopKm;

            }
        }
    }
}
