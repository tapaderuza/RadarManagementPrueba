package com.example.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class HttpUtil {

    private static String username = "alvaro"; // Credenciales reales
    private static String password = "alvaro"; // Credenciales reales

    private static String getBasicAuth() {
        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    public static String sendGetRequest(String endpoint) throws Exception {
        URL url = new URL("http://localhost:8081" + endpoint);  // Asegúrate de que el puerto es 8081
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", getBasicAuth());

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            String errorMessage = "Failed : HTTP error code : " + responseCode;
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            errorMessage += "\nResponse: " + response.toString();
            throw new RuntimeException(errorMessage);
        }
    }
}

