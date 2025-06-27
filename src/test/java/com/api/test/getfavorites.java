package com.api.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class getfavorites {

    // Inner class to map the incoming request JSON data
    public static class RequestFav {
        private String p_id;
        private String type;
        private String userId;

        // Getters and Setters for the RequestFav class
        public String getP_id() {
            return p_id;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    @Test
    public void getFav() throws IOException {
        // Base URI for the RestAssured API
        RestAssured.baseURI = "https://darwinapi.edvak.com:3000";

        // Load request data from the JSON file
        RequestFav data = readRequestDataFromFile("fav.json");

        // Print details to the console (optional for debugging)
        System.out.println("Location Name: " + data.getP_id());
        System.out.println("Location ID: " + data.getType());
        System.out.println("Practice Name: " + data.getUserId());

        // Send a POST request to the API using RestAssured
        Response response = RestAssured
            .given()
            .header("Accept", "application/json, text/plain, */*")
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjdkYWRhYjliMjM0NjI3Y2RlZDVmYTU3IiwicF9pZCI6IjY1NDRmNjU1MDZhNzI3YTU3YmQwZGIxNiIsImZpcnN0X25hbWUiOiJKb2huIiwibGFzdF9uYW1lIjoiVGVzdHByb3ZpZGVyIiwiZW1haWwiOiJza3N1c2FyaUBlZHZhay5jb20iLCJpYXQiOjE3NTA5Mjc3ODh9.Pm3Tm9dQVN93Q3fMFJMZDSnD1WXEoz2iNoykozHI88s") // Replace with your actual token
            .header("Content-Type", "application/json")
            .body(data)
            .when()
            .post("/face-sheet/get-favourites");

        // Print the response status code and the body for debugging
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response:\n" + response.asPrettyString());

        // Assert that the status code is 200 (OK)
        assertEquals(200, response.getStatusCode());

        // Assert that the message in the response is "Success"
        response.then().body("message", equalTo("Success"));

        // Extract the MRN from the "result" field in the response JSON
        String mrn = response.jsonPath().getString("result");
        System.out.println("Patient MRN: " + mrn);

        // Assert that the MRN is not null
        assertNotNull(mrn);
    }

    // Helper method to read the request data from the JSON file
    private RequestFav readRequestDataFromFile(String filePath) throws IOException {
        // Load the JSON file from the resources directory
        URL resource = getClass().getClassLoader().getResource(filePath);

        // If the resource is not found, throw an exception
        if (resource == null) {
            throw new IllegalArgumentException("Resource not found: " + filePath);
        }

        // Use Jackson to parse the JSON file into a RequestFav object
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(resource.getFile()), RequestFav.class);
    }
}
