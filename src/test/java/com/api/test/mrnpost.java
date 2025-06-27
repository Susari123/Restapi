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

public class mrnpost {

    // POJO class to hold the data from the JSON file
    public static class RequestData {
        private String locationName;
        private String locationId;
        private String practiceName;

        // Getters and setters
        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public String getPracticeName() {
            return practiceName;
        }

        public void setPracticeName(String practiceName) {
            this.practiceName = practiceName;
        }
    }

    @Test
    public void getMrn() throws IOException {
        // Set the base URI for the API
        RestAssured.baseURI = "https://darwinapi.edvak.com:3000";

        // Read JSON data from the file located in src/main/resources
        RequestData data = readRequestDataFromFile("/data.json"); // Use relative path from resources folder

        // Print the data read from the file
        System.out.println("Location Name: " + data.getLocationName());
        System.out.println("Location ID: " + data.getLocationId());
        System.out.println("Practice Name: " + data.getPracticeName());

        // Send POST request with headers and body
        Response response = RestAssured
                .given()
                .header("Accept", "application/json, text/plain, */*")
                .header("Accept-Language", "en-IN,en;q=0.9")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjdkYWRhYjliMjM0NjI3Y2RlZDVmYTU3IiwicF9pZCI6IjY1NDRmNjU1MDZhNzI3YTU3YmQwZGIxNiIsImZpcnN0X25hbWUiOiJKb2huIiwibGFzdF9uYW1lIjoiVGVzdHByb3ZpZGVyIiwiZW1haWwiOiJza3N1c2FyaUPlZHZhay5jb20iLCJpYXQiOjE3NTA5Mjc3ODh9.Pm3Tm9dQVN93Q3fMFJMZDSnD1WXEoz2iNoykozHI88s")
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .header("Origin", "https://darwinapi.edvak.com")
                .header("Referer", "https://darwinapi.edvak.com/")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-site")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36")
                .header("location_id", "6a0820ec7834")
                .header("moment", "America/Chicago")
                .header("p_id", "6544f65506a727a57bd0db16")
                .header("pid", "6544f65506a727a57bd0db16")
                .header("sec-ch-ua", "\"Google Chrome\";v=\"137\", \"Chromium\";v=\"137\", \"Not/A)Brand\";v=\"24\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("userid", "67dadab9b234627cded5fa57")
                .body(data) // Use the data from the JSON file
                .when()
                .post("/patients/get-mrn"); // Endpoint

        // Print the response to inspect
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response:\n" + response.asPrettyString());

        // Assert that the response status code is 200 (OK)
        assertEquals(200, response.getStatusCode());

        // Assert that the message is "Success"
        response.then().body("message", equalTo("Success"));

        // Extract the MRN from the "result" field
        String mrn = response.jsonPath().getString("result");
        System.out.println("Patient MRN: " + mrn);

        // Assert that the MRN is not null
        assertNotNull(mrn);
    }

    // Method to read the JSON file and map it to the RequestData object
    private RequestData readRequestDataFromFile(String filePath) throws IOException {
        // Get the file from the classpath (resources folder)
        URL resource = getClass().getResource(filePath); // Use relative path for resources
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(resource.getFile()), RequestData.class);
    }
}
