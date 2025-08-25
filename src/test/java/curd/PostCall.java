package curd;

import files.Payload;
import files.Reusable;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class PostCall {
    public static void main(String[] args){
        RestAssured.baseURI="https://rahulshettyacademy.com";
String response=given().log().all().queryParam("key","qaclick123").header("Content-type","application/json")
        .body(Payload.Addplace()).when().post("maps/api/place/add/json)").then().assertThat().statusCode(200)
        .body("scope",equalTo("APP")).extract().response().asString();
        System.out.println(response);
        JsonPath js=Reusable.rawToJson(response);
        String placeId=js.getString("place_id");
        System.out.println(placeId);

        //update place
        String newAddress= "55 summer walk, India";
        given().log().all().queryParam("key","qaclick123").header("Content-type","application/json")
                .body("{\n" +
                      "    \"place_id\": \""+placeId+"\",\n" +
                      "    \"address\": \""+newAddress+"\",\n" +
                      "    \"key\": \"qaclick123\"\n" +
                      "}").when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200);

        String getResponse = given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId).when()
                .get("maps/api/place/get/json").then().assertThat().statusCode(200).extract().response().asString();

        JsonPath jss1= Reusable.rawToJson(getResponse);
        String actualAddress=jss1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress,newAddress);
    }
}
