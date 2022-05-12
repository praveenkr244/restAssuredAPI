package com.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class RequestSpecificationExample {
    RequestSpecification reqspecification;
    @BeforeClass
    public void beforeClass(){

        /* reqspecification=with()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key","PMAK-61b79eb45d15b00051eeb3d9-ae5061ecbf05affc7b2a05d37c5139edbb")
                 .log().all(); //logging all the resquest
*/
        RequestSpecBuilder reqspecBuilder=new RequestSpecBuilder();
        reqspecBuilder.setBaseUri("https://api.getpostman.com");
        reqspecBuilder.addHeader("x-api-key","PMAK-61b79eb45d15b00051eeb3d9-ae5061ecbf05affc7b2a05d37c5139edbb");
        reqspecBuilder.log(LogDetail.ALL);
        reqspecification=reqspecBuilder.build();
    }

    @Test
    public void validate_status_code(){

                Response response=given(reqspecification).get("/workspaces").then().log().all().extract().response();
                assertThat(response.statusCode(),is(equalTo(200)));



    }

    @Test
    public void validate_status_code1(){

        Response response=given(reqspecification)
                .header("dummy header","value")
                .get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(),is(equalTo(200)));
        assertThat(response.path("workspaces[0].type"),equalTo("team"));

    }
}
