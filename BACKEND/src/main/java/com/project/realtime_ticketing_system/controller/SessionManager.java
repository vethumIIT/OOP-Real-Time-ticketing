package com.project.realtime_ticketing_system.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public class SessionManager {
    public static boolean isLoggedIn(HttpSession session){
        System.out.println("Session username: "+session.getAttribute("username"));
        return session.getAttribute("username") != null;
    }

    public static boolean isLoggedIn(HttpSession session, String userType){
        System.out.println("Session username: "+session.getAttribute("username"));
        System.out.println("Session UserType: "+session.getAttribute("UserType"));
        return session.getAttribute("username") != null && session.getAttribute("UserType")==userType;
    }

    public static ResponseCookie setCookies(HttpSession session){
        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", session.getId()) // Set a meaningful session ID or token
            .httpOnly(true)  // Protect from JavaScript access (security)
            .secure(false)   // Set to 'true' in production with HTTPS
            .maxAge(-1)  // Cookie expiration
            .path("/")
            .sameSite("None")  // SameSite=None for cross-origin requests
            .build();

        return cookie;
    }


}
