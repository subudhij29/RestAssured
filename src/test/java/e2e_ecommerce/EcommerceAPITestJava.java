package e2e_ecommerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class EcommerceAPITestJava {
    public static void main(String[] args) {
        RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
                .build();
        LoginClass login=new LoginClass();
        login.setUserEmail("jitendrasubudhi76522@gmail.com");
        login.setUserPassword("Boss0209@");
        RequestSpecification reqLogin=given().relaxedHTTPSValidation().log().all().spec(req).body(login);
        LoginResponse loginResponse=reqLogin.when().post("/api/ecom/auth/login").then().extract()
                .response().as(LoginResponse.class);
        System.out.println(loginResponse.getToken());
        String token=loginResponse.getToken();
        System.out.println(loginResponse.getUserId());
        String userId=loginResponse.getUserId();


        //Add product
        RequestSpecification addProductBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization",token).build();

        RequestSpecification  reqAddProduct=given().log().all().spec(addProductBaseReq).param("productName", "Laptop")
                .param("productAddedBy", userId).param("productCategory", "fashion")
                .param("productSubCategory", "shirts").param("productPrice", "11500")
                .param("productDescription", "Lenova").param("productFor", "men")
                .multiPart("productImage",new File("/Users/jitendra_subudhi/Downloads/laptop.png"));

        String addProductResponse=reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all()
                .extract().response().asString();
        JsonPath js= new JsonPath(addProductResponse);
        String productId=js.get("productId");

      //Create Order
        RequestSpecification createOrderBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization",token).setContentType(ContentType.JSON).build();

        OrderDetails oderDetails= new OrderDetails();
        oderDetails.setCountry("India");
        oderDetails.setProductOrderedId(productId);

        List<OrderDetails> orderList=new ArrayList<>();
        orderList.add(oderDetails);

        Orders order=new Orders();
        order.setOrders(orderList);

        RequestSpecification createOrderReq=given().log().all().spec(createOrderBaseReq).body(order);
        String responseOrder=createOrderReq.when().post("/api/ecom/order/create-order").then().log().all()
                .extract().response().asString();
        System.out.println(responseOrder);
        JsonPath js2=new JsonPath(responseOrder);
        List<String> orderIds=js2.getList("orders");
        String orderId=orderIds.get(0);



        //Delete product
        RequestSpecification deleteProductBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization",token).setContentType(ContentType.JSON).build();

        RequestSpecification deleteReq=given().spec(deleteProductBaseReq).pathParams("productId",productId);
        String responseDelete=deleteReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all()
                .extract().response().asString();
        JsonPath js1= new JsonPath(responseDelete);
        Assert.assertEquals("Product Deleted Successfully",js1.get("message"));

        //Delete Order
        RequestSpecification deleteOrderBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization",token).build();

        RequestSpecification delectOrder=given().spec(deleteOrderBaseReq).pathParams("orderId",orderId);
        String responseOrderDelete=delectOrder.when().delete("/api/ecom/order/delete-order/{orderId}").then().log().all()
                .extract().response().asString();
        JsonPath js4=new JsonPath(responseOrderDelete);
        Assert.assertEquals("Orders Deleted Successfully",js4.get("message"));


    }
}
