package com.project.realtime_ticketing_system.repositories;

import com.project.realtime_ticketing_system.models.Booking;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {

    private final String url = "jdbc:sqlite:mydatabase.db";


}
