package com.project.realtime_ticketing_system.repositories;

import com.project.realtime_ticketing_system.controller.BookingController;
import com.project.realtime_ticketing_system.models.Vendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class VendorRepository {

    private static final Logger logger = LoggerFactory.getLogger(VendorRepository.class);
    DatabaseManager db = new DatabaseManager();
    private final String url = "jdbc:sqlite:mydatabase.db";

    public String registerUser(Vendor vendor){
        List<Object> parameters = new ArrayList<>();
        parameters.add(vendor.getName());
        parameters.add(vendor.getEmail());
        parameters.add(vendor.getPassword());

        String response = db.writeDatabase("INSERT INTO vendor (username, email, password) VALUES (?, ?, ?)", parameters);

        return response;
    }

    public Vendor getUser(String username){
        Vendor vendor;
        List<Object> parameters = new ArrayList<>();
        parameters.add(username);

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM vendor WHERE username=?", parameters);

        if (result==null){
            return null;
        }else {
            /*System.out.println(this.toVendor(result.get(0)).getId());
            System.out.println(this.toVendor(result.get(0)).getName());
            System.out.println(this.toVendor(result.get(0)).getPassword());
            System.out.println(this.toVendor(result.get(0)).getEmail());*/
            return this.toVendor(result.get(0));
        }
    }

    public Vendor getUserById(Long vendorId){
        Vendor vendor;
        List<Object> parameters = new ArrayList<>();
        parameters.add(vendorId);

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM vendor WHERE id=?", parameters);

        if (result==null){
            return null;
        }else {
            /*System.out.println(this.toVendor(result.get(0)).getId());
            System.out.println(this.toVendor(result.get(0)).getName());
            System.out.println(this.toVendor(result.get(0)).getPassword());
            System.out.println(this.toVendor(result.get(0)).getEmail());*/
            return this.toVendor(result.get(0));
        }
    }

    public Vendor toVendor(Map<String, Object> record){
        //System.out.println("Passowoed record: "+record.get("password"));
        return new Vendor(
                ((Number) record.get("id")).longValue(),
                (String) record.get("username"),
                (String) record.get("email"),
                (String) record.get("password")
        );
    }

}
