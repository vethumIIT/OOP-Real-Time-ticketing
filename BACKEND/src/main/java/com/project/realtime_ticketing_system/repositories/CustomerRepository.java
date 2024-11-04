package com.project.realtime_ticketing_system.repositories;

import com.project.realtime_ticketing_system.models.Customer;
import org.springframework.stereotype.Repository;
import org.sqlite.SQLiteErrorCode;

import java.sql.*;

@Repository
public class CustomerRepository {

    private final String url = "jdbc:sqlite:mydatabase.db";

    public String registerUser(Customer customer){

        try{
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(this.url);

            PreparedStatement stmt = c.prepareStatement("INSERT INTO customer (username, email, password) VALUES (?, ?, ?)");

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPassword());

            stmt.executeUpdate();
            stmt.close();
            c.close();

            System.out.println("Account Registered Successfully!");

        }catch (SQLException e){
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code){
                System.out.println("Duplicate record in users");
                return "Exists";
            }
            else {
                System.out.println("Error: "+e.getMessage());
                return "Failed";
            }
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return "Failed";
        }
        return "Success";
    }

    public Customer getUser(String username){

        Customer customer;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(this.url);

            PreparedStatement stmt = c.prepareStatement("SELECT * FROM customer WHERE username=?");

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                customer = new Customer(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")

                );
            }else {
                System.out.println("No user by that name was found!");
                return null;
            }
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return null;
        }
        return customer;

    }


}
