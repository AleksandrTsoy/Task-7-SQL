package service;

import dao.CourseDAO;
import dao.StudentDAO;
import entity.Course;
import entity.Student;
import util.DataInput;
import util.FileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;


public class StudentService {
    StudentDAO studentDAO;
    CourseDAO courseDAO;
    DataInput userInput;
    FileReader fileReader;

    public StudentService(StudentDAO studentDAO, CourseDAO courseDAO, DataInput userInput, FileReader fileReader) {
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.userInput = userInput;
        this.fileReader = fileReader;
    }

    public void addStudent(Student student) throws SQLException, IOException, URISyntaxException {
        studentDAO.addStudent(student);
        System.out.printf("Student ID: %d. Group ID: %d. Full Name: %s %s. \n",
                student.getStudentID(),
                student.getGroupID(),
                student.getFirstName(),
                student.getLastName());
    }

    public void addCoursesToStudents() throws SQLException, IOException {
        List<Course> courses = courseDAO.getCourses();
        List<Student> students = studentDAO.getAllStudents();
        for (int i = 0; i < students.size(); i++) {
            int countCourses = (int) (Math.random() * 3);
            List<Integer> idCourses = new Random().ints(countCourses, 1, 10)
                    .boxed()
                    .collect(toList());
            for (int j = 0; j < idCourses.size(); j++) {
                studentDAO.assignStudentOnCourse(students.get(i), courses.get(idCourses.get(j)));
            }
        }
    }

    public void addNewStudent() throws SQLException, IOException {
        System.out.print("Input first name: ");
        String studentFirstName = userInput.inputData();
        System.out.print("Input last name: ");
        String studentLastName = userInput.inputData();
        int studentId = studentDAO.getAllStudents().size() + 1;
        studentDAO.addStudent(new Student(studentId, 0 , studentFirstName, studentLastName));
        System.out.printf("Student %s %s add successfully. \n", studentFirstName, studentLastName);
    }

    public void addStudentOnCourse() throws SQLException, IOException {
        System.out.print("Input student ID: ");
        int studentId = Integer.parseInt(userInput.inputData());
        System.out.println("We have next courses. Choose one:");
        List<Course> coursesList = courseDAO.getCourses();
        for (Course course : coursesList) {
            System.out.printf("%d. %s\n", course.getId(), course.getCourseName());
        }
        System.out.print("Input course ID: ");
        int courseId = Integer.parseInt(userInput.inputData());
        Student student = studentDAO.getStudentById(studentId).get();
        Course course = coursesList.get(courseId - 1);
        studentDAO.assignStudentOnCourse(student, course);
        System.out.println("Add student on course successfully.");
    }

    public void removeStudent() throws SQLException, IOException {
        System.out.print("Input student ID: ");
        int studentID = Integer.parseInt(userInput.inputData());
        studentDAO.removeStudentById(studentID);
        System.out.println("delete successfully.");
    }

    public void studentsRelatedToCourse() throws SQLException, IOException {
        System.out.print("Input course name: ");
        String courseName = userInput.inputData();
        System.out.println("\nStudents related to course " + courseName + ":");
        Course course = courseDAO.getCourseByName(courseName).get();
        List<Student> studentRelatedCourses = studentDAO.getStudentsByCourseId(course.getId());
        for (Student student : studentRelatedCourses) {
            System.out.printf("Student ID: %d. Full Name: %s %s. \n",
                    student.getStudentID(),
                    student.getFirstName(),
                    student.getLastName());
        }
    }

    public void removeStudentFromCourse() throws SQLException, IOException {
        System.out.print("input student ID: ");
        int studentId = Integer.parseInt(userInput.inputData());
        List<Course> studentRelatedCourses = courseDAO.getCoursesByStudentId(studentId);
        System.out.println("Student have this courses:");
        for (Course course : studentRelatedCourses) {
            System.out.printf("%d. %s.\n", course.getId(), course.getCourseName());
        }
        System.out.print("\ninput course number what do you delete: ");
        int courseId = Integer.parseInt(userInput.inputData());
        studentDAO.deleteStudentFromCourse(studentId, courseId);
        System.out.println("Delete successfully.");
    }
}
