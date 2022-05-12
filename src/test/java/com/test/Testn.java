package com.test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class Testn {
//    @org.testng.annotations.Test
//    public void test(){
//  given()
//          .baseUri("https://api.getpostman.com")
//          .header("x-api-key","PMAK-61b79eb45d15b00051eeb3d9-ae5061ecbf05affc7b2a05d37c5139edbb")
//  .when()
//          .get("/workspaces")
//  .then()
//          .log().all()
//          .assertThat().statusCode(200);
//
//    }

    @Test
    public void responseVerify(){
        given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key","PMAK-61b79eb45d15b00051eeb3d9-ae5061ecbf05affc7b2a05d37c5139edbb")
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("workspaces.name", hasItems("Team Workspace", "My Workspace", "APIAutomation"),
                        "workspaces.type",hasItems("team", "personal", "personal"),
                        "workspaces[0].type",is(equalTo("team")),
                        "workspaces.size" ,equalTo(3))
        ;

    }

    @Test
    public void extract_response(){
        Response res=given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key","PMAK-61b79eb45d15b00051eeb3d9-ae5061ecbf05affc7b2a05d37c5139edbb")
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response();
        System.out.println("response" + res.asString());
        ;

    }


    @Test
    public void extract_single_value_response(){
        Response res=given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key","PMAK-61b79eb45d15b00051eeb3d9-ae5061ecbf05affc7b2a05d37c5139edbb")
                .when()
                .get("/workspaces")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response();
        JsonPath jsonPath=new JsonPath(res.asString());
        //System.out.println("worksapce_name" + res.path("workspaces[0].name"));
        System.out.println("worksapce_name" + jsonPath.getString("workspaces[0].name"))
        ;

    }

    @Test
    public void extract_single_value_response_1(){
        String str=given()
                .baseUri("https://api.getpostman.com")
                .header("x-api-key","PMAK-61b79eb45d15b00051eeb3d9-ae5061ecbf05affc7b2a05d37c5139edbb")
                .log().parameters()
                .log().ifValidationFails()
                .when()
                .get("/workspaces")
                .then()
                .log().ifError()
                .log().all()
                .log().headers()

                .assertThat().statusCode(200)
                .extract().body().path("workspaces[0].name");
       // JsonPath jsonPath=new JsonPath(res.asString());
        //System.out.println("worksapce_name" + res.path("workspaces[0].name"));
        System.out.println("worksapce_name :" + str);
        assertThat(str,equalTo("Team Workspace"));
        Assert.assertEquals(str,"Team Workspace");

    }


}
