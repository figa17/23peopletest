package com.demo.test.controllers;

import com.demo.test.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Felipe González Alfaro on 18-03-20.
 */
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getToken() {

        return "{\"token\": \"" + this.tokenService.getJwtToken() + "\"}";
    }
}
