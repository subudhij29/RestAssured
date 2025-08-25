package pojo;

public class GetCourse {
    private String instructor;
    private String url;
    private String services;
    private String expertise;
    private Courses courses;
    private String linkedIn;
    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }






    // Getter and Setter for instructor
    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    // Getter and Setter for url
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // Getter and Setter for services
    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    // Getter and Setter for expertise
    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    // Getter and Setter for courses
    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }
}
