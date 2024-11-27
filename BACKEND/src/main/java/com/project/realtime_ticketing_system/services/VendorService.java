package com.project.realtime_ticketing_system.services;

import com.project.realtime_ticketing_system.controller.BookingController;
import com.project.realtime_ticketing_system.models.Customer;
import com.project.realtime_ticketing_system.models.Vendor;
import com.project.realtime_ticketing_system.repositories.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VendorService {

    private static final Logger logger = LoggerFactory.getLogger(VendorService.class);
    private final VendorRepository vendorRepository;

    @Autowired
    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public String register(Vendor vendor){ // DONE
        vendor.setPassword(PasswordHash.hash(vendor.getPassword().toString()));
        String response = "Error Occurred";
        ServiceLock.lock.lock();
        try {
            response = vendorRepository.registerUser(vendor);// Database WRITE
        }finally {
            ServiceLock.lock.unlock();
        }
        return response;
    }


    public List<String> login(Vendor vendor){ // DONE
        Vendor returnedVendor = vendorRepository.getUser(vendor.getName());// Database Read

        boolean correct_passwd;
        if (returnedVendor == null){
            return new ArrayList<String>(List.of("Not Found"));
        }else {
            correct_passwd=PasswordHash.checkPassword(vendor.getPassword(), returnedVendor.getPassword());
            if (correct_passwd){
                System.out.println("Test session completed");

                return new ArrayList<String>(Arrays.asList("Logged In", vendor.getName()));
            }else {
                return new ArrayList<String>(List.of("Incorrect Password"));
            }
        }
    }

    public Vendor getVendor(String username){
        return vendorRepository.getUser(username);
    }
    public Vendor getVendorById(Long vendorId){
        return vendorRepository.getUserById(vendorId);
    }
}
