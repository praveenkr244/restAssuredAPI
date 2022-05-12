package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePostReqAsArrayList {
    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder reqSpecBuilder=new RequestSpecBuilder();
        reqSpecBuilder.setBaseUri("https://0e24a144-5341-47ae-9901-4f836cd5e276.mock.pstmn.io");
        reqSpecBuilder.addHeader("x-mock-match-request-headers","true");
        reqSpecBuilder.setContentType(ContentType.JSON);
        reqSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification=reqSpecBuilder.build();


        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.responseSpecification=responseSpecBuilder.build();
    }

    @Test
    public void validate_post_request_AsArray_List(){
        HashMap<String,String> obj1=new HashMap<String,String>();
        obj1.put("id","5001");
        obj1.put("type","None");
        HashMap<String,String> obj2=new HashMap<String,String>();
        obj2.put("id","5009");
        obj2.put("type","Glazed");
        List<HashMap> jsonList=new ArrayList<HashMap>();
        jsonList.add(obj1);
        jsonList.add(obj2);

        Response response= with()
                .body(jsonList)
                .post("/post");

        assertThat(response.<String>path("msg"),equalTo("success"));

    }
}
