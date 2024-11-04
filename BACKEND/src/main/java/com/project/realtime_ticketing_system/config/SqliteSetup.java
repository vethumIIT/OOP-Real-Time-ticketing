package com.project.realtime_ticketing_system.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SqliteSetup {

    private final String url = "jdbc:sqlite:mydatabase.db";

    public void setup(){
        Connection c;
        Statement stmt;

        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(this.url);
            System.out.println("Database Opened successfully!");

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS customer (\n" +
                     "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                     "    username TEXT NOT NULL UNIQUE,\n" +
                     "    email TEXT NOT NULL UNIQUE,\n" +
                     "    password TEXT NOT NULL\n" +
                     ");\n" +
                     "\n" +
                     "CREATE TABLE IF NOT EXISTS vendor (\n" +
                     "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                     "    username TEXT NOT NULL UNIQUE,\n" +
                     "    email TEXT NOT NULL UNIQUE,\n" +
                     "    password TEXT NOT NULL\n" +
                     ");\n" +
                     "\n" +
                     "CREATE TABLE IF NOT EXISTS bookings (\n" +
                     "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                     "    customer_id INTEGER NOT NULL,\n" +
                     "    event_id INTEGER NOT NULL, \n" +
                     "    number_of_tickets INTEGER NOT NULL,\n" +
                     "    booking_date TEXT NOT NULL\n" +
                     ");\n" +
                     "\n" +
                     "CREATE TABLE IF NOT EXISTS tickets (\n" +
                     "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                     "    event_id INTEGER NOT NULL,\n" +
                     "    booking_id INTEGER,\n" +
                     "    is_available TEXT NOT NULL\n" +
                     ");\n" +
                     "\n" +
                     "CREATE TABLE IF NOT EXISTS event (\n" +
                     "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                     "    vendor_id INTEGER NOT NULL,\n" +
                     "    event_name TEXT NOT NULL,\n" +
                     "    event_date TEXT NOT NULL,\n" +
                     "    tickets_sold INTEGER NOT NULL,\n" +
                     "    total_tickets INTEGER NOT NULL,\n" +
                     "    price REAL NOT NULL\n" +
                     ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            System.out.println("Table Created Successfully!");

        }catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }

}
