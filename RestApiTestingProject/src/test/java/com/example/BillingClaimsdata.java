package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class BillingClaimsdata {
    @Test
    public void testGetBillingData() throws IOException {
        // Base URI
        RestAssured.baseURI = "https://darwinapi.edvak.com:3000";

        // API Endpoint
        String endpoint = "/billing/get-all-coding-billing";

        // Authorization Token
        String authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjYzMGI1MDE0N2IyNDdjN2M4ZWU4ZWE3IiwicF9pZCI6IjY2MzBiNTAxNDdiMjQ3YzdjOGVlOGVhNyIsImZpcnN0X25hbWUiOiJSb2JlcnQiLCJsYXN0X25hbWUiOiJXaWxsaWFtc1R3byIsImVtYWlsIjoic291cmF2c3VzYXJpMzExQGdtYWlsLmNvbSIsImlhdCI6MTczMzIxNjM2NX0.-vtVXqoa4wc4OyYOC5nlQF6e-RWoKh91oFlx2X3kJRU";

        // Send GET Request with headers
        Response response = given()
                .header("Authorization", authToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json, text/plain, */*")
                .header("Location_ID", "954f0ddb2bf9")
                .header("Moment", "America/New_York")
                .when()
                .get(endpoint)
                .then()
                .log().ifValidationFails() // Only log response if validation fails
                .extract()
                .response();

        // Validate Status Code
        assertEquals(200, response.statusCode(), "Expected status code is not returned");

        // Directory and File Setup
        String directoryPath = "src/test/resources/output";
        String outputFilePath = directoryPath + "/BillingData.js";

        // Create the directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create directory at: " + directoryPath);
        }

        // Prepare Response as JavaScript
        String responseBody = response.asString(); // Get response as a String
        String jsContent = "const billingDataResponse = " + responseBody + ";\nexport default billingDataResponse;";

        // Write to JS File
        try (FileWriter file = new FileWriter(outputFilePath)) {
            file.write(jsContent);
            System.out.println("Response saved to JS file: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing response to file: " + e.getMessage());
            throw e;
        }
    }
}
