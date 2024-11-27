package com.project.realtime_ticketing_system.repositories;

import com.project.realtime_ticketing_system.controller.BookingController;
import com.project.realtime_ticketing_system.models.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sqlite.SQLiteErrorCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {

    DatabaseManager db = new DatabaseManager();
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);

    private final String url = "jdbc:sqlite:mydatabase.db";

    public String registerUser(Customer customer){
        List<Object> parameters = new ArrayList<>();
        parameters.add(customer.getName());
        parameters.add(customer.getEmail());
        parameters.add(customer.getPassword());

        String result = db.writeDatabase("INSERT INTO customer (username, email, password) VALUES (?, ?, ?)",
                parameters);

        if(result.equals("Success")){
            logger.debug("Successfully created user");
        } else if (result.equals("Exists")) {
            logger.debug("That user already exists");
        } else{
            logger.debug("Failed to create user");
        }
        return result;
    }

    public Customer getUser(String username){

        logger.debug("Running getUser in CustomerRepo");

        Customer customer;

        List<Object> parameters = new ArrayList<>();
        parameters.add(username);

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM customer WHERE username=?", parameters);

        if (result==null){
            logger.debug("CustomerRepo: No customer named {}",username);
            return null;
        } else if (result.size()==0) {
            logger.debug("CustomerRepo: results were empty for {}",username);
            return null;
        } else {
            //List<Customer> customers = new ArrayList<>();
            customer=this.toCustomer(result.get(0));
            return customer;
        }

    }

    public Customer toCustomer(Map<String, Object> record){
        return new Customer(
                ((Number) record.get("id")).longValue(),
                (String) record.get("username"),
                (String) record.get("email"),
                (String) record.get("password")
        );
    }
}
