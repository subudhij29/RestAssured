package curd;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPars {
    public static void main(String[] args) {
        JsonPath js=new JsonPath(Payload.CoursePrice());
        int count=js.getInt("courses.size()");
        System.out.println(count);
        String price=js.getString("dashboard.purchaseAmount");
        System.out.println(price);
        for (int i = 0; i < count; i++) {
            String courseTitle=js.get("courses["+i+"].title");
            System.out.println(js.get("courses["+i+"].price").toString());
            System.out.println(courseTitle);
        }
        System.out.println("Print number of copies sold by RPA");
        for (int i = 0; i < count; i++) {
            String courseTitle=js.get("courses["+i+"].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int copies=js.get("courses["+i+"].copies");
                System.out.println("Number of copies:"+copies);
                break;
            }
        }
    }
}
