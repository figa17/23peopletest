package com.demo.test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Felipe González Alfaro on 18-03-20.
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public String index(){
        return "Index";
    }
}
