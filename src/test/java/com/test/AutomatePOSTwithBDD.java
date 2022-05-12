package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomatePOSTwithBDD {

    @BeforeClass
    public void beforeClass(){

        RequestSpecBuilder reqSpecBuilder=new RequestSpecBuilder();
        reqSpecBuilder.setBaseUri("https://api.getpostman.com");
        reqSpecBuilder.addHeader("x-api-key","PMAK-61b79eb45d15b00051eeb3d9-ae5061ecbf05affc7b2a05d37c5139edbb");
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
    public void validate_status_code_bdd_style(){
        String payload="{\n" +
                "   \"workspace\":{\n" +
                "      \"name\":\"addedWorksapcePOST\",\n" +
                "      \"type\":\"personal\",\n" +
                "      \"description\":\"Added worksapce through post call\"\n" +
                "   }\n" +
                "}";
        given()
                .body(payload)
        .when()
                .post("/workspaces")
                .then()
                .log().all()
                .assertThat()
                .body("workspace.name",equalTo("addedWorksapcePOST"),
                        "workspace.id",matchesPattern("^[a-z0-9-]{36}$"));




    }
    @Test
    public void validate_status_code_non_bdd_style(){
        String payload="{\n" +
                "   \"workspace\":{\n" +
                "      \"name\":\"addedWorksapcePOST23\",\n" +
                "      \"type\":\"personal\",\n" +
                "      \"description\":\"Added worksapce through post call\"\n" +
                "   }\n" +
                "}";

       Response response= with()
                .body(payload)
                .post("/workspaces");
         assertThat(response.<String>path("workspace.name"),equalTo("addedWorksapcePOST23"));
        assertThat(response.<String>path("workspace.id"),matchesPattern("^[a-z0-9-]{36}$"));

    }

    @Test
    public void validate_post_request_fromFile(){
        File file=new File("src/main/resources/CreateWorkspace.json");

        Response response= with()
                .body(file)
                .post("/workspaces");

        assertThat(response.<String>path("workspace.id"),matchesPattern("^[a-z0-9-]{36}$"));

    }

    @Test
    public void validate_post_request_AsMap(){
        HashMap<String,Object> mainObject=new HashMap<String,Object>();
        HashMap<String,String> masterObject=new HashMap<String,String>();
        masterObject.put("name","WS1234");
        masterObject.put("type","personal");
        masterObject.put("description","Added worksapce through post call");

        mainObject.put("workspace",masterObject);

        Response response= with()
                .body(mainObject)
                .post("/workspaces");

        assertThat(response.<String>path("workspace.id"),matchesPattern("^[a-z0-9-]{36}$"));

    }


}
