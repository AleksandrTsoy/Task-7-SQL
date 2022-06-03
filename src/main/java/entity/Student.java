package entity;

public class Student {

    private int studentID;
    private int groupID;
    private String firstName;
    private String lastName;

    public Student(Integer studentID, Integer groupID, String firstName, String lastName) {
        this.studentID = studentID;
        this.groupID = groupID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getStudentID() {
        return studentID;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
