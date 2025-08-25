package curd;

import files.Payload;
import files.Reusable;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Dynamicjson {
@Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle){
    RestAssured.baseURI="http://216.10.245.166";
    String response=given().header("Content-type","application/json").
            body(Payload.AddBook(isbn,aisle)).when().post("Library/Addbook.php")
            .then().assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
            .extract().response().asString();
    System.out.println(response);
    JsonPath js= Reusable.rawToJson(response);
    String id=js.get("ID");
    System.out.println(id);
}
@DataProvider(name="BooksData")
    public Object[][] getData(){
  return new Object[][]{{"wert","3245"},{"werrt","32445"},{"wgert","22345"}};
}
}
