package com.ecotrekker.publictransportdistance.model;

import lombok.Data;
import java.util.Map;

@Data
public class PublicTransportRoutes {
    private Map<String, VehicleRoute> vehicles;
}
