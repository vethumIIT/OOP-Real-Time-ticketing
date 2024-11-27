package com.project.realtime_ticketing_system.controller;

import jakarta.servlet.http.HttpSession;

public class SessionManager {
    public static boolean isLoggedIn(HttpSession session){
        return session.getAttribute("username") != null;
    }

    public static boolean isLoggedIn(HttpSession session, String userType){
        return session.getAttribute("username") != null && session.getAttribute("UserType")==userType;
    }

}
