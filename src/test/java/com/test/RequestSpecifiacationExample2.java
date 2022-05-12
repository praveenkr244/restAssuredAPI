package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;


public class RequestSpecifiacationExample2 {

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
        RestAssured.requestSpecification=reqspecBuilder.build();
    }
@BeforeMethod
public void printrequestspec(){
    QueryableRequestSpecification queryableRequestSpecification= SpecificationQuerier.query(requestSpecification);
   System.out.println( "$$$$$$$$$$$"+queryableRequestSpecification.getBaseUri() +"$$$$$$$$$$$");
    System.out.println( "$$$$$$$$$$$"+queryableRequestSpecification.getHeaders()+"$$$$$$$$$$$");
}
    @Test
    public void validate_status_code(){

        Response response=get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(),is(equalTo(200)));
        //QueryableRequestSpecification queryableRequestSpecification= SpecificationQuerier.query(requestSpecification);

    }

    @Test
    public void validate_status_code1(){

        Response response= get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(),is(equalTo(200)));
        assertThat(response.path("workspaces[0].type"),equalTo("team"));

    }

}
