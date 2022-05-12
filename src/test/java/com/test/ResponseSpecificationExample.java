package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ResponseSpecificationExample {

@BeforeClass
    public void beforeClass(){

        /* reqspecification=with()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key","PMAK-61b79eb45d15b00051eeb3d9-ae5061ecbf05affc7b2a05d37c5139edbb")
                 .log().all(); //logging all the resquest
*/
        RequestSpecBuilder reqSpecBuilder=new RequestSpecBuilder();
        reqSpecBuilder.setBaseUri("https://api.getpostman.com");
        reqSpecBuilder.addHeader("x-api-key","PMAK-61b79eb45d15b00051eeb3d9-ae5061ecbf05affc7b2a05d37c5139edbb");
        reqSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification=reqSpecBuilder.build();

       /* responseSpecification=RestAssured.expect()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all();
*/
        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
    RestAssured.responseSpecification=responseSpecBuilder.build();
    }
    @BeforeMethod
    public void printrequestspec(){
        QueryableRequestSpecification queryableRequestSpecification= SpecificationQuerier.query(requestSpecification);
        System.out.println( "$$$$$$$$$$$"+queryableRequestSpecification.getBaseUri() +"$$$$$$$$$$$");
        System.out.println( "$$$$$$$$$$$"+queryableRequestSpecification.getHeaders()+"$$$$$$$$$$$");

    }


    @Test
    public void validate_status_code11(){

     get("/workspaces");


    }

    @Test
    public void validate_status_code21(){

        Response response= get("/workspaces").then().extract().response();

        assertThat(response.path("workspaces[0].type"),equalTo("team"));

    }
}
