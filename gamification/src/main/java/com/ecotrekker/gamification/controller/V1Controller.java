package com.ecotrekker.gamification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecotrekker.gamification.model.GamificationRequest;

@RestController
@RequestMapping(value = "/v1")
public class V1Controller {

    @PostMapping("/calc/points")
    public ResponseEntity<?> calculatePoints(@RequestBody GamificationRequest request) {
        /*
         * Use BasePoints class to get base point and use RushHour and then Preferred to modify the points.
         */

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
    
}
