package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomateDelete {
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
    public void validateDeleteResponse_BDD(){
        String workspaceID="73aad316-4039-4afe-af18-f02a2217cb17";

        given()
                .pathParam("worksapceID",workspaceID)
                .when()
                .delete("/workspaces/{worksapceID}")
                .then().log().all()
                .assertThat()
                .body("workspace.id",matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id",equalTo(workspaceID));

    }

    @Test
    public void validateDeleteResponse_NonBdd(){
        String workspaceID1="21d19167-224b-40af-92ed-38ab670d1532";


        Response response= with()
                .pathParam("worksapceID1",workspaceID1)
                .put("/workspaces/{workspaceID1}");



        assertThat(response.<String>path("workspace.id"),matchesPattern("^[a-z0-9-]{36}$"));
        assertThat(response.<String>path("workspace.id"),equalTo(workspaceID1));

    }
}
