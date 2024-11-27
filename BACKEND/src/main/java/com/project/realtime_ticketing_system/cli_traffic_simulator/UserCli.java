package com.project.realtime_ticketing_system.cli_traffic_simulator;

import com.google.gson.Gson;
import com.project.realtime_ticketing_system.models.Customer;
import com.project.realtime_ticketing_system.models.Event;
import com.project.realtime_ticketing_system.repositories.EventRepository;
import com.project.realtime_ticketing_system.services.EventService;

import java.util.HashMap;
import java.util.Map;


public class UserCli {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String userType;

    private String cookies = "";

    TrafficSimulator trafficSimulator = new TrafficSimulator();

    public UserCli(Long id, String name, String email, String password, String userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String toJson(){
        return "{" +
                "\"id\": "+this.id+",\n" +
                "    \"name\": \""+this.name+"\",\n" +
                "    \"email\": \""+this.email+"\",\n" +
                "    \"password\": \""+this.password+"\"" +
                "}";
    }

    public String hashMapToJson(HashMap<String, Object> map){

        Gson gson = new Gson();
        return gson.toJson(map);

    }

    public HashMap<String, Object> jsonToHashmap(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, HashMap.class);
    }

    public String register(){
        Map<String, Object> reply = trafficSimulator.sendPostRequest(this.toJson(), "api/"+this.userType+"/register", this.cookies);
        return reply.get("stringResponse").toString();
    }

    public String login() {
        Map<String, Object> reply = trafficSimulator.sendPostRequest(this.toJson(), "api/"+this.userType+"/login", this.getCookies());
        String status = reply.get("stringResponse").toString();

        System.out.println("logged in to "+this.getName());

        if (status.equals("Logged In")){
            this.setCookies(reply.get("cookies").toString());

            System.out.println(this.getCookies());

            HashMap<String, Object> map = new HashMap<>();
            map.put("name",this.getName());
            String customerInfo = trafficSimulator.sendPostRequest(this.toJson(), "api/"+this.userType+"/get", this.getCookies()).get("stringResponse").toString();

            if (customerInfo==null){
                System.out.println("Error Finding User. null value returned");
                return status;
            }

            this.id = ((Number) this.jsonToHashmap(customerInfo).get("id")).longValue();
            //this.email = this.jsonToHashmap(customerInfo).get("email").toString();

            System.out.println(this.cookies);
        }
        return status;
    }

    public Long getEventIdByName(String name){
        HashMap<String,Object> map = new HashMap<>();
        map.put("eventId",0);
        map.put("vendorId",0);
        map.put("eventName",name);
        map.put("ticketsSold",0);
        map.put("totalTickets",0);
        map.put("price",0);


        Map<String, Object> reply = trafficSimulator.sendPostRequest(this.hashMapToJson(map), "api/event/get_by_name", this.cookies);

        System.out.println(jsonToHashmap(reply.get("stringResponse").toString()).get("eventId"));
        if (reply==null){
            System.out.println("reply was null");
        }else if (reply.get("stringResponse").toString()==null){
            System.out.println("stringResponse was null");
        }else{
            Long eventId = ((Number) this.jsonToHashmap(reply.get("stringResponse").toString()).get("eventId")).longValue();
            return eventId;
        }
        System.out.println("Failed to find event named "+name);
        return null;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}