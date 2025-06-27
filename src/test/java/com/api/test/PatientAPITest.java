package com.api.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PatientAPITest {

    @Test
    public void getPatientsList() {
        // Base URI
        RestAssured.baseURI = "https://darwinapi.edvak.com:3000";

        // Send GET request with headers
        Response response = RestAssured
                .given()
                .header("Accept", "application/json, text/plain, */*")
                .header("Accept-Language", "en-IN,en;q=0.9")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjdkYWRhYjliMjM0NjI3Y2RlZDVmYTU3IiwicF9pZCI6IjY1NDRmNjU1MDZhNzI3YTU3YmQwZGIxNiIsImZpcnN0X25hbWUiOiJKb2huIiwibGFzdF9uYW1lIjoiVGVzdHByb3ZpZGVyIiwiZW1haWwiOiJza3N1c2FyaUBlZHZhay5jb20iLCJpYXQiOjE3NTA4MzQyNTJ9.eLYILWbpY9nZUlgUDe2YHsC2nsPnWUxyAaYiVoF_Dxc")
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
                .get("/patients/patientsList?page=0&pageSize=20&searchTerm=&status=false");

        // Print response
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response:\n" + response.asPrettyString());

        // Basic assertion
        assertEquals(200, response.getStatusCode());

        // Example: get first patient's name
        String firstName = response.jsonPath().getString("result[1][0].first_name");
        System.out.println("✅ First Patient Name: " + firstName);
        String lastName = response.jsonPath().getString("result[1][0].last_name");
        System.out.println("Last Name:" +lastName);
        // Access the first patient's email
String firstPatientEmail = response.jsonPath().getString("result[1][0].emails['0']");
System.out.println("First Patient's Email: " + firstPatientEmail);
        String fullname = response.jsonPath().getString("result[1][0].searchParam.fullName");
        System.out.println("FullName" +fullname);
        String dob = response.jsonPath().getString("result[1][0].searchParam.dob");
        System.out.println("dob" +dob);
        String providerName = response.jsonPath().getString("result[1][0].created_by[0].first_name");
        System.out.println("provider name " + providerName);
        
// Ensure email is not null
assertNotNull(firstPatientEmail);
    }
}
