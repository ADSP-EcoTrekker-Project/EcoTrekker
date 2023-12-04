package com.ecotrekker.gridco2cache;

import com.ecotrekker.gridco2cache.electricitymaps.ElectricityMapsApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ElectricityMapsApiTest {

    @Test
    void testSuccessfulConnectToAPI() throws Exception {
        // Mocking HTTP response for successful connection
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);

        // Use reflection or change connectToAPI to public for testing
        HttpResponse<String> response = ElectricityMapsApi.connectToAPI();

        assertNotNull(response);
        assertEquals(200, response.statusCode());
    }


    @Test
    void testReadCarbonIntensityFromAPI() {
        // Mocking HTTP response with sample body
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockResponse.body()).thenReturn("Sample response body");

        // Use reflection or change readCarbonIntensityFromAPI to public for testing
        String result = ElectricityMapsApi.readCarbonIntensityFromAPI(mockResponse);

        assertEquals("Sample response body", result);
    }

}
