package com.demo.test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Felipe González Alfaro on 18-03-20.
 */
@RestController
public class TokenController {

    @GetMapping("/token")
    public String getToken() {

        return "";
    }
}