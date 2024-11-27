package com.project.realtime_ticketing_system.controller;


import com.project.realtime_ticketing_system.config.AllowedOrigins;
import com.project.realtime_ticketing_system.models.Vendor;
import com.project.realtime_ticketing_system.services.VendorService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = AllowedOrigins.allowedOrigins, allowCredentials = "true")
@RequestMapping("/api/vendor")
public class VendorController {

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);
    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @RequestMapping("/register")// create
    public ResponseEntity<String> register(@RequestBody Vendor vendor, HttpSession session){
        return ResponseEntity.ok(vendorService.register(vendor));
        //return "This is where the backend starts registering vendors";
    }

    @RequestMapping("/login") // read
    public ResponseEntity<String> log_in(@RequestBody Vendor vendor, HttpSession session){

        List<String> list = vendorService.login(vendor);

        if (list.get(0).equals("Logged In")) {
            session.setAttribute("username", list.get(1));
            session.setAttribute("UserType", "vendor");
        }

        return ResponseEntity.ok(list.get(0));
        //return "This is where the backend starts validating credentials and logging in the vendors";
    }

    @RequestMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }

    @RequestMapping("/update") // update
    public String update(){
        return "This is where the process of changing vendor info starts";
    }

    @RequestMapping("/delete") // delete
    public String delete(){
        return "This is where the process of deleting a vendor starts";
    }

    @RequestMapping("/get")
    public ResponseEntity<Vendor> getVendor(@RequestBody Vendor vendor, HttpSession session){
        if (session.getAttribute("username")==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else if (session.getAttribute("username").equals(vendor.getName())) {
            Vendor v = vendorService.getVendor(vendor.getName());
            v.setPassword("");
            return ResponseEntity.ok(v);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}