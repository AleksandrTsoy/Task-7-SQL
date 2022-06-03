package dao.Implementation;

import dao.CourseDAO;
import entity.Course;
import dao.ConnectionHandler;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDaoImpl implements CourseDAO {
    private ConnectionHandler connectionHandler;
    public static final String SELECT_ALL_COURSES = "SELECT * FROM students.courses";
    public static final String SELECT_COURSES_WHERE_COURSE_NAME = "SELECT * FROM students.courses WHERE course_name = '%s'";
    public static final String SELECT_COURSES_WHERE_STUDENT_ID = "SELECT * FROM students.students_courses WHERE student_id = '%d'";
    public static final String INSERT_COURSE = "INSERT INTO students.courses (course_name, course_description) VALUES (?,?)";

    public CourseDaoImpl(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public List<Course> getCourses() throws SQLException, IOException {
        List<Course> coursesList = new ArrayList<>();
        try (Connection connection = connectionHandler.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet courses = statement.executeQuery(SELECT_ALL_COURSES);
            while (courses.next()) {
                int courseId = courses.getInt("course_id");
                String courseName = courses.getString("course_name");
                String courseDescription = courses.getString("course_description");
                coursesList.add(new Course(courseId, courseName, courseDescription));
            }
        }
        return coursesList;
    }

    public List<Course> getCoursesByStudentId(int studentId) throws SQLException, IOException {
        List<Course> coursesList = new ArrayList<>();
        try (Connection connection = connectionHandler.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet courses = statement.executeQuery(String.format(SELECT_COURSES_WHERE_STUDENT_ID, studentId));
            while (courses.next()) {
                int courseID = courses.getInt("course_id");
                String courseName = courses.getString("course_name");
                coursesList.add(new Course(courseID, courseName, null));
            }
        }
        return coursesList;
    }

    public Optional<Course> getCourseByName(String courseName) throws SQLException, IOException {
        try (Connection connection = connectionHandler.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet courses = statement.executeQuery(String.format(SELECT_COURSES_WHERE_COURSE_NAME, courseName));
            Optional<ResultSet> resultSet = Optional.of(courses);
            if (resultSet.isPresent()) {
                Course course = null;
                while (courses.next()) {
                    int courseId = courses.getInt("course_id");
                    String name = courses.getString("course_name");
                    String courseDescription = courses.getString("course_description");
                    course = new Course(courseId, name, courseDescription);
                }
                return Optional.of(course);
            }
            else {
                return Optional.empty();
            }
        }
    }

    public void addCourse(Course course) throws SQLException, IOException {
        try (Connection connection = connectionHandler.getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_COURSE);
            insertStatement.setString(1, course.getCourseName());
            insertStatement.setString(2, course.getCourseDescription());
            insertStatement.executeUpdate();
        }
    }
}
