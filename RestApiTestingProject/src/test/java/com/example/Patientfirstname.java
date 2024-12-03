package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class Patientfirstname {
    @Test
    public void testGetPatientdata() throws IOException {
        // Base URI
        RestAssured.baseURI = "https://darwinapi.edvak.com:3000";

        // API Endpoint
        String endpoint = "/patients/patientsList?page=0&pageSize=20&searchTerm=&status=false";

        // Send GET Request with headers
        Response response = given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjYzMGI1MDE0N2IyNDdjN2M4ZWU4ZWE3IiwicF9pZCI6IjY2MzBiNTAxNDdiMjQ3YzdjOGVlOGVhNyIsImZpcnN0X25hbWUiOiJSb2JlcnQiLCJsYXN0X25hbWUiOiJXaWxsaWFtc1R3byIsImVtYWlsIjoic291cmF2c3VzYXJpMzExQGdtYWlsLmNvbSIsImlhdCI6MTczMzIxNjM2NX0.-vtVXqoa4wc4OyYOC5nlQF6e-RWoKh91oFlx2X3kJRU")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json, text/plain, */*")
                .header("Location_ID", "954f0ddb2bf9")
                .header("Moment", "America/New_York")
                .when()
                .get(endpoint)
                .then()
                .log().all() // Log response for debugging
                .extract()
                .response();

        // Validate Status Code
        assertEquals(200, response.statusCode(), "Expected status code is not returned");

        // Debug: Print Response Body
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

        // Extract the list of patients (second element in the "result" array)
        List<String> firstNames = response.jsonPath().getList("result[1].first_name");
        List<String> lastNames = response.jsonPath().getList("result[1].last_name");

        // Debug: Print extracted data
        System.out.println("Extracted First Names: " + firstNames);
        System.out.println("Extracted Last Names: " + lastNames);

        // Validate that data exists
        if (firstNames == null || lastNames == null || firstNames.isEmpty() || lastNames.isEmpty()) {
            System.out.println("No valid patient data found!");
            return;
        }

        // Prepare JSON content for extracted details
        StringBuilder jsonContent = new StringBuilder();
        jsonContent.append("[\n");
        for (int i = 0; i < firstNames.size(); i++) {
            String firstName = firstNames.get(i) != null ? firstNames.get(i) : "null";
            String lastName = lastNames.get(i) != null ? lastNames.get(i) : "null";

            jsonContent.append("  {\n");
            jsonContent.append("    \"first_name\": \"").append(firstName).append("\",\n");
            jsonContent.append("    \"last_name\": \"").append(lastName).append("\"\n");
            jsonContent.append("  }");
            if (i < firstNames.size() - 1) {
                jsonContent.append(",\n");
            }
        }
        jsonContent.append("\n]");

        // Directory and File Setup
        String directoryPath = "src/test/resources/output";
        String outputFilePath = directoryPath + "/PatientDetails.json";

        // Create the directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Directory created at: " + directoryPath);
            } else {
                System.out.println("Failed to create directory at: " + directoryPath);
            }
        }

        // Write JSON content to file
        try (FileWriter file = new FileWriter(outputFilePath)) {
            file.write(jsonContent.toString());
            System.out.println("Patient details saved to JSON file: " + outputFilePath);
        }
    }
}
