package com.project.realtime_ticketing_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.DefaultCookieSerializer;
//import org.springframework.session.web.http.DefaultCookieSerializer;

//@Configuration
public class SessionConfig {
    @Bean
    public DefaultCookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setSameSite("None"); // Force SameSite=None
        serializer.setUseSecureCookie(true); // Set to false for HTTP (development only)
        return serializer;
    }
}
