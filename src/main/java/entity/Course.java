package entity;

public class Course {

    private int id;

    private String courseName;

    private String courseDescription;

    public Course(int courseID, String courseName, String courseDescription) {
        this.id = courseID;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }
}
