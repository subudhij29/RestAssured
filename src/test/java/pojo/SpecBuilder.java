package pojo;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilder {
    public static void main(String[] args) {
        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("French-IN");
        p.setPhone_number("(+91) 983 893 3937");
        p.setName("Frontline house");
        List<String> myList = new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");
        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

        ResponseSpecification responsspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification res = given().spec(req).body(p);

        Response response = res.when().post("maps/api/place/add/json)").then().spec(responsspec)
                .extract().response();
        String responseString = response.asString();
        System.out.println(responseString);
    }

}
