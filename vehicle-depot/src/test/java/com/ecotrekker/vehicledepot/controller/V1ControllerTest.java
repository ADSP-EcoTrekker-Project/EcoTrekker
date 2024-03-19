package com.ecotrekker.vehicledepot.controller;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.ecotrekker.vehicledepot.config.depot.TransportLine;
import com.ecotrekker.vehicledepot.config.depot.VehicleDepot;
import com.ecotrekker.vehicledepot.messages.DepotMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class V1ControllerTest {

    @InjectMocks
    private V1Controller v1Controller;

    @Mock
    private Map<String, TransportLine> lineMap;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLineElectricalShare_Success() {
        // Mocking the request
        DepotMessage request = new DepotMessage();
        request.setLine("mockLine");

        // Mocking the lineMap
        TransportLine mockLine = new TransportLine();
        VehicleDepot mockDepot = new VehicleDepot();
        Map<String, Double> mockVehicles = new HashMap<>();
        mockVehicles.put("vehicle1", 1.0);
        mockVehicles.put("vehicle2", 2.0);
        mockDepot.setVehicles(mockVehicles);
        mockLine.setDepot(mockDepot);

        // Stubbing the lineMap
        when(lineMap.get("mockLine")).thenReturn(mockLine);

        // Calling the method under test
        ResponseEntity<?> responseEntity = v1Controller.getLineElectricalShare(request);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        DepotMessage response = (DepotMessage) responseEntity.getBody();
        assertEquals(mockVehicles, response.getVehicles());
    }

    @Test
    public void testGetLineElectricalShare_NotFound() {
        // Mocking the request
        DepotMessage request = new DepotMessage();
        request.setLine("nonExistingLine");

        // Stubbing the lineMap to return null
        when(lineMap.get("nonExistingLine")).thenReturn(null);

        // Calling the method under test
        ResponseEntity<?> responseEntity = v1Controller.getLineElectricalShare(request);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetLineElectricalShare_InternalServerError() {
        // Mocking the request
        DepotMessage request = new DepotMessage();
        request.setLine("mockLine");

        // Stubbing the lineMap to throw an exception
        when(lineMap.get("mockLine")).thenThrow(new RuntimeException("Some internal error occurred"));

        // Calling the method under test
        ResponseEntity<?> responseEntity = v1Controller.getLineElectricalShare(request);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

}
