package authorization;
import files.Reusable;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
public class Authentication{
    public static void main(String[] args) {
        String[] coursesTitle={"Selenium Webdriver Java","Cypress","Protractor"};
        String response=given().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials").formParam("scope","trust")
                .when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
        System.out.println(response);

        JsonPath js= Reusable.rawToJson(response);
        String accessToken=js.get("access_token");

        GetCourse gc=given().queryParam("access_token",accessToken).when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getCourses().getWebAutomation().get(2).getCourseTitle());

        List<WebAutomation> wa= gc.getCourses().getWebAutomation();
        for (int i = 0; i < wa.size(); i++) {
            if(wa.get(i).getCourseTitle().equalsIgnoreCase("Protractor")){
                System.out.println(wa.get(i).getPrice());
            }
        }

        List<WebAutomation> allcourses=gc.getCourses().getWebAutomation();
        ArrayList<String> actualCourse= new ArrayList<String>();
        for (WebAutomation allcours : allcourses) {
            actualCourse.add(allcours.getCourseTitle());
        }

        List<String> expectedCourse= Arrays.asList(coursesTitle);
        Assert.assertEquals(expectedCourse, actualCourse);
    }
}
