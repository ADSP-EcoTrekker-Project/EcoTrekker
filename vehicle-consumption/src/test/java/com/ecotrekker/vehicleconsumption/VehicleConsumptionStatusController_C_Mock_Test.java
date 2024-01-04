package com.ecotrekker.vehicleconsumption;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleConsumptionStatusController_C_Mock_Test {

    @Autowired
	private MockMvc mockMvc;

    @Test
    void testAliveEndpoint() throws Exception {
        assertThat(
            this.mockMvc.perform(get("/status/alive")
            ).andDo(print()
            ).andExpect(status().isOk()
            ).andExpect(content().string(containsString("Vehicle Consumption Service is alive!")))
        );
    }

    @Test
    void testReadyEndpoint() throws Exception {
        assertThat(
            this.mockMvc.perform(get("/status/ready")
            ).andDo(print()
            ).andExpect(status().isOk()
            ).andExpect(content().string(containsString("Vehicle Consumption Service is ready!")))
        );
    }
    
}
