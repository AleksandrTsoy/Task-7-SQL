package controller;

public class MenuController {
    public void getMenu() {
        System.out.println("\nApplication menu:\n" +
                "a. Find all groups with less or equals student count\n" +
                "b. Find all students related to course with given name\n" +
                "c. Add new student\n" +
                "d. Delete student by STUDENT_ID\n" +
                "e. Add a student to the course (from a list)\n" +
                "f. Remove the student from one of his or her courses\n" +
                "stop. Exit");
        System.out.print("\nChoose an item (a, b, c, d, e, f or stop): ");
    }
}
