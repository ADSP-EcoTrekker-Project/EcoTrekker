package com.ecotrekker.vehicleconsumption.exceptions;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecotrekker.vehicleconsumption.config.vehicles.VehicleBeanFactory_C;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandler_C {
    
    private static Logger logger = LoggerFactory.getLogger(VehicleBeanFactory_C.class);

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> anyException (Exception exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Internal Server Error"));
    }

}
