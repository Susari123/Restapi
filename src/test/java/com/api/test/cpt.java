package com.api.test;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class cpt {

    @Test
    public void getCptModifiersList() {
        // Set the base URI for the API
        RestAssured.baseURI = "https://darwinapi.edvak.com:3000";

        // Send GET request with headers
        Response response = RestAssured
                .given()
                .header("Accept", "application/json, text/plain, */*")
                .header("Accept-Language", "en-IN,en;q=0.9")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjdkYWRhYjliMjM0NjI3Y2RlZDVmYTU3IiwicF9pZCI6IjY1NDRmNjU1MDZhNzI3YTU3YmQwZGIxNiIsImZpcnN0X25hbWUiOiJKb2huIiwibGFzdF9uYW1lIjoiVGVzdHByb3ZpZGVyIiwiZW1haWwiOiJza3N1c2FyaUBlZHZhay5jb20iLCJpYXQiOjE3NTA5Mjc3ODh9.Pm3Tm9dQVN93Q3fMFJMZDSnD1WXEoz2iNoykozHI88s")
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
                .when()
                .get("/billing/getAllcptModifiersLists"); // Endpoint

        // Print response for inspection
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response:\n" + response.asPrettyString());

        // Basic assertion to check if status code is 200 (OK)
        assertEquals(200, response.getStatusCode());

        // Check if the message field is "Success" (not status)
        // response.then().body("message", equalTo("Success"));

        // Example: If you want to validate if the result array is not empty (if result is an array)
        response.then().body("result.size()", greaterThan(0)); // Checks that the result array has at least one item
    }
}
