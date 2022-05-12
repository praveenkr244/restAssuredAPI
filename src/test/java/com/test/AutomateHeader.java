package com.test;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class AutomateHeader {

@Test
public void mutiple_header(){
    HashMap<String,String> headers=new HashMap<>();
    headers.put("header","value2");
    headers.put("x-mock-match-request-headers","header");

   /* Header header=new Header("header","value1");
    Header headerMatcher=new Header("x-mock-match-request-headers","header");
    Headers headers=new Headers(header,headerMatcher);*/
    given()
            .baseUri("https://0e24a144-5341-47ae-9901-4f836cd5e276.mock.pstmn.io")
            .headers(headers)


            .when()
                    .get("/get")
                    .then()
            .header("headerResponse","resValue2")
                    .log().all()
                    .assertThat()
                    .statusCode(200);


}

    @Test
    public void mutiple_header_extrectMultipleVlaueHeader(){
        HashMap<String,String> headers=new HashMap<>();
        headers.put("header","value2");
        headers.put("x-mock-match-request-headers","header");

   /* Header header=new Header("header","value1");
    Header headerMatcher=new Header("x-mock-match-request-headers","header");
    Headers headers=new Headers(header,headerMatcher);*/
        Headers extractHeaderValue=given()
                .baseUri("https://0e24a144-5341-47ae-9901-4f836cd5e276.mock.pstmn.io")
                .headers(headers)


                .when()
                .get("/get")
                .then()
                .header("headerResponse","resValue2")
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().headers();

        List<String> values=extractHeaderValue.getValues("multiValueHeader");
       for(String value:values){
           System.out.println(value);
       }

    }
}