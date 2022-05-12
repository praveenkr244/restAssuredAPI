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

public class AutomatePUT {

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
    public void validatePutResponse_BDD(){
        String workspaceID="73aad316-4039-4afe-af18-f02a2217cb17";
        String payload="{\n" +
                "   \"workspace\":{\n" +
                "      \"name\":\"addedWorksapceforPut996\",\n" +
                "      \"type\":\"personal\",\n" +
                "      \"description\":\"Added worksapce through post call\"\n" +
                "   }\n" +
                "}";
        given()
                    .body(payload)
                .pathParam("worksapceID",workspaceID)
                    .when()
                    .put("/workspaces/{worksapceID}")
                .then().log().all()
                .assertThat()
            .body("workspace.name",equalTo("addedWorksapceforPut996"),
                "workspace.id",matchesPattern("^[a-z0-9-]{36}$"),
                    "workspace.id",equalTo(workspaceID));



}

    @Test
    public void validatePutResponse_NonBdd(){
        String workspaceID1="d61eba63-b105-4999-b5b6-7e1afff3afd1";
        String payload="{\n" +
                "   \"workspace\":{\n" +
                "      \"name\":\"addedWorksapceforPut321\",\n" +
                "      \"type\":\"personal\",\n" +
                "      \"description\":\"Added worksapce through post call\"\n" +
                "   }\n" +
                "}";

        Response response= with()
                .body(payload)
                .pathParam("worksapceID1",workspaceID1)
                .put("/workspaces/{workspaceID1}");


        assertThat(response.<String>path("workspace.name"),equalTo("addedWorksapceforPut321"));
        assertThat(response.<String>path("workspace.id"),matchesPattern("^[a-z0-9-]{36}$"));
        assertThat(response.<String>path("workspace.id"),equalTo(workspaceID1));

    }

}
