package com.project.realtime_ticketing_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @RequestMapping("/register")// create
    public String register(){
        return "This is where the backend starts registering vendors";
    }

    @RequestMapping("/login") // read
    public String log_in(){
        return "This is where the backend starts validating credentials and logging in the vendors";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "This is where the vendor logs out";
    }

    @RequestMapping("/update") // update
    public String update(){
        return "This is where the process of changing vendor info starts";
    }

    @RequestMapping("/delete") // delete
    public String delete(){
        return "This is where the process of deleting a vendor starts";
    }


}