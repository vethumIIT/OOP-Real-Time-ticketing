package com.project.realtime_ticketing_system.cli_traffic_simulator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficSimulator {

    public Map<String, Object> sendPostRequest(String content, String path, String cookies){
        try {
            // URL to send POST request to (local server)
            URL url = new URL("http://localhost:8080/"+path);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Set request headers
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            if (cookies!=null) {
                connection.setRequestProperty("Cookie", cookies);
            }

            // Add POST data
            String urlParameters = content;
            try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
                out.writeBytes(urlParameters);
                out.flush();
            }

            // Check the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println("Response: " + response.toString());

                Map<String, List<String>> headerFields = connection.getHeaderFields();
                List<String> cookiesHeader = headerFields.get("Set-Cookie");

                Map<String, Object> returnValue = new HashMap<>();
                returnValue.put("stringResponse",response.toString());
                if (cookiesHeader!=null) {
                    returnValue.put("cookies", cookiesHeader.get(0));
                }else {
                    returnValue.put("cookies", null);
                }

                System.out.println(returnValue.get("cookies"));

                return returnValue;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
