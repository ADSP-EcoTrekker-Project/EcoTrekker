package com.ecotrekker.restapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/status")
public class RestAPIStatusController {

    @GetMapping("/alive")
    public ResponseEntity<String> getAlive() {
        return new ResponseEntity<String>("Rest API is alive!", null, HttpStatus.OK);
    }

    @GetMapping("/ready")
    public ResponseEntity<String> getReady() {
        return new ResponseEntity<String>("Rest API is ready!", null, HttpStatus.OK);
    }

}
