package com.project.realtime_ticketing_system.cli_application;

import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.models.Event;
import com.project.realtime_ticketing_system.models.Vendor;
import com.project.realtime_ticketing_system.services.BookingService;
import com.project.realtime_ticketing_system.services.EventService;
import com.project.realtime_ticketing_system.services.TicketService;
import com.project.realtime_ticketing_system.services.VendorService;
import org.apache.tomcat.util.net.jsse.JSSEUtil;

import java.io.Console;
import java.util.List;
import java.util.Scanner;



public class CLIVendor {

    private Scanner input = new Scanner(System.in);
    private Console console = System.console();

    private BookingService bookingService;
    private EventService eventService;
    private VendorService vendorService;
    private TicketService ticketService;

    private Vendor user;

    public CLIVendor(BookingService bookingService, EventService eventService, VendorService vendorService, TicketService ticketService) {
        this.bookingService = bookingService;
        this.eventService = eventService;
        this.vendorService = vendorService;
        this.ticketService = ticketService;
    }


    public String loginUser(){
        boolean exited = false;
        String status="Exiting";
        String username = "";

        while (!exited) {
            System.out.print("Enter Username: ");
            username = input.nextLine();

            if (username.isEmpty()){
                exited=true;
                return "Exiting";
            }

            System.out.print("Enter Password: ");
            String password = input.nextLine();
            /*
            char[] passwordArray = console.readPassword("Enter password: ");
            String password = new String(passwordArray);
             */

            if(validateUser(username, password)){
                exited=true;
            }

        }

        this.user=vendorService.getVendor(username);
        return "Logged In";

    }

    public boolean validateUser(String username, String password){

        String login_status="";
        login_status = vendorService.login(new Vendor(
                0L,
                username,
                "",
                password
        )).get(0);

        if (login_status.equals("Not Found")){
            System.out.println("No such user exists");
        } else if (login_status.equals("Incorrect Password")) {
            System.out.println("Password was Incorrect");
        } else if (login_status.equals("Logged In")) {
            System.out.println("Logged in Successfully!");
            return true;
        }
        return false;
    }

    public void runMenu(){
        String login_status = this.loginUser();

        if (login_status.equals("Exiting")){
            return;
        }
        boolean exitMenu = false;
        int choice = 0;
        while (!exitMenu){
            System.out.println("===================================");
            System.out.println("\t\tMenu\t\t");
            System.out.println("===================================");
            System.out.println();
            System.out.println("1 - Create Event");
            System.out.println("2 - View Events");
            System.out.println("3 - Remove Event");
            System.out.println("4 - Add Ticket");
            System.out.println("5 - View Tickets");
            System.out.println("6 - Remove Tickets");
            System.out.println("7 - Exit");
            System.out.print("Enter your Choice: ");

            try {
                choice = Integer.parseInt(input.nextLine());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

            if (choice==1){
                this.createEvent();
            } else if (choice==2) {
                this.viewEvents();
            } else if (choice==7) {
                exitMenu=true;
                break;
            } else{
                continue;
            }
        }

    }

    public void createEvent(){
        int event_year = 0;
        int month = 0;
        int day = 0;
        double ticket_price = 0.0;

        System.out.println("Enter Event name: ");
        String event_name = input.nextLine();

        System.out.println("Enter Event description: ");
        String event_description = input.nextLine();

        System.out.println("Enter event year: ");
        try {
            event_year = Integer.parseInt(input.nextLine());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Enter event month (in number form): ");
        try {
            month = Integer.parseInt(input.nextLine());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Enter event day: ");
        try {
            day = Integer.parseInt(input.nextLine());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Enter ticket price: ");
        try {
            ticket_price = Double.parseDouble(input.nextLine());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        Long vendor_id = this.user.getId();

        String status = eventService.addEvent(
            new Event(
                0L,
                vendor_id,
                event_name,
                "description",
                "location",
                event_year+"/"+month+"/"+day,
                0,
                0
            )
        );
        switch (status) {
            case "Success" -> System.out.println("Successfully created event!");
            case "Error" -> System.out.println("Failed to create event due to unexpected error");
            case "Exists" -> System.out.println("That event already exists.");
        }
    }

    public void viewEvents(){
        List<Event> events = eventService.getEventByVendorId(this.user.getId());

        if (events==null || events.isEmpty()){
            System.out.println("No events by this vendor!");
            return;
        }

        for (Event event: events){
            System.out.println("\n++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Event ID: "+event.getEventId());
            System.out.println("Event Name: "+event.getEventName());
            System.out.println("Event Date: "+event.getEventDate());
            System.out.println("Event tickets sold: "+event.getTicketsSold());
            System.out.println("Total tickets of event: "+event.getTotalTickets());
            System.out.println("Ticket Price: "+event.getPrice());
        }
    }
}
