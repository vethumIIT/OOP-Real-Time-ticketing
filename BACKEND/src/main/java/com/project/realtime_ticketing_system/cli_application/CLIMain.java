package com.project.realtime_ticketing_system.cli_application;

import com.project.realtime_ticketing_system.RealtimeTicketingSystemApplication;
import com.project.realtime_ticketing_system.config.SqliteSetup;
import com.project.realtime_ticketing_system.models.Event;
import com.project.realtime_ticketing_system.services.*;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class CLIMain {
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        SqliteSetup sqliteSetup = new SqliteSetup();
        sqliteSetup.setup();
        ConfigurableApplicationContext context = new SpringApplicationBuilder(RealtimeTicketingSystemApplication.class)
                .web(WebApplicationType.NONE)  // No web server
                .run(args);

        BookingService bookingService = context.getBean(BookingService.class);
        EventService eventService = context.getBean(EventService.class);
        VendorService vendorService = context.getBean(VendorService.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        TicketService ticketService = context.getBean(TicketService.class);

        System.out.println("Running CLI...");
        boolean validUserType = false;
        int userType=0;
        while (!validUserType){
            System.out.println("What type of user are you?");
            System.out.println("1 - Consumer");
            System.out.println("2 - Producer");
            System.out.println("3 - Exit");
            System.out.print("Enter your Choice: ");
            try {
                userType = Integer.parseInt(input.nextLine());
            }catch (Exception e){
                System.out.println(e.getMessage());
                continue;
            }

            if (userType<1 || userType>3){
                System.out.println("Invalid input. Try Again!");
            }else {
                validUserType=true;
            }
        }

        if (userType==3){

        }else {
            if (userType==2){
                CLIVendor vendor = new CLIVendor(bookingService, eventService, vendorService, ticketService);
                vendor.runMenu();
            }
        }

        try {
            List<Event> events =  eventService.getAvailableEvents();
            for (Event e: events){
                System.out.println(e.getEventName());
            }
            System.out.println();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //CompletableFuture

        context.close();
    }
}
