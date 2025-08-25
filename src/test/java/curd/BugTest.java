package curd;

import files.Reusable;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;

public class BugTest {
    public static void main(String[] args) {
        RestAssured.baseURI="https://subudhij29.atlassian.net/";
        String createIssue=given().header("Content-type","application/json")
                .header("Authorization","Basic c3VidWRoaWoyOUBnbWFpbC5jb206QVRBVFQzeEZmR0YwMUU5STBhNDJVYlZzb0JMQzVXSHhBcUp6TWZYWUVDR2lDTzdXOUlDX0Q1X243SFoxckMyYnEtczZFRExia3RUaVN5dzNHMEE4bjlobFdPRjVKWHphRklzN3RyallJajZlZDZHdFBXekJMUDJlRXJCT3I3b2N5TnRQenhvM3YwaFQ3dWNta1BJb0VvZDcxMG9YNWVBU1ZSWWZoa3psa3VmTW94QzdSTXNKMDJNPTI5RkFDQ0RC")
                .header("X-Atlassian-Token","no-check")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "        \"project\": {\n" +
                        "            \"key\": \"SCRUM\"\n" +
                        "        },\n" +
                        "        \"summary\": \"Webpage is not working\",\n" +
                        "        \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\n" +
                        "        \"issuetype\": {\n" +
                        "            \"name\": \"Bug\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}")
                .log().all().post("rest/api/2/issue").then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js= Reusable.rawToJson(createIssue);
        String issueID=js.get("id");
        System.out.println(issueID);

        given()
                .pathParams("key",issueID)
                .header("Authorization","Basic c3VidWRoaWoyOUBnbWFpbC5jb206QVRBVFQzeEZmR0YwMUU5STBhNDJVYlZzb0JMQzVXSHhBcUp6TWZYWUVDR2lDTzdXOUlDX0Q1X243SFoxckMyYnEtczZFRExia3RUaVN5dzNHMEE4bjlobFdPRjVKWHphRklzN3RyallJajZlZDZHdFBXekJMUDJlRXJCT3I3b2N5TnRQenhvM3YwaFQ3dWNta1BJb0VvZDcxMG9YNWVBU1ZSWWZoa3psa3VmTW94QzdSTXNKMDJNPTI5RkFDQ0RC")
                .header("X-Atlassian-Token","no-check").multiPart("file",new File("/Users/jitendra_subudhi/Downloads/Resume_30_10_2024_07_55_00_PM.pdf"))
                .log().all().post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
    }


}
