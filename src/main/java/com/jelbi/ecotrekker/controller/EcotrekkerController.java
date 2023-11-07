package com.jelbi.ecotrekker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ecotrekker")
public class EcotrekkerController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World!";
    }

}