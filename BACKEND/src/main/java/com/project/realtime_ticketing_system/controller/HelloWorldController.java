package com.project.realtime_ticketing_system.controller;


import com.project.realtime_ticketing_system.config.AllowedOrigins;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = AllowedOrigins.allowedOrigins, allowCredentials = "true")
@RequestMapping("/api/")
public class HelloWorldController {

    @GetMapping("/")
    public String hi(){
        return "Hello from another package";
    }

    @GetMapping({"/abc", "/abc/"})
    public String abc(){
        return "ABC Page";
    }

}
