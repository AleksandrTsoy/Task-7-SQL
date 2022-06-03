package dao.Implementation;

import dao.ConnectionHandler;
import dao.StudentDAO;
import entity.Course;
import entity.Student;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDAO {
    private ConnectionHandler connectionHandler;
    public static final String SELECT_ALL_STUDENTS = "SELECT * FROM students.students";
    public static final String SELECT_STUDENTS_WHERE_STUDENT_ID = "SELECT * FROM students.students WHERE student_id = '%d'";
    public static final String SELECT_STUDENTS_WHERE_GROUP_ID = "SELECT * FROM students.students WHERE group_id = '%d'";
    public static final String SELECT_STUDENTS_WHERE_COURSE_ID = "SELECT * FROM students.students_courses WHERE course_id = '%d'";
    public static final String INSERT_STUDENT = "INSERT INTO students.students (group_id, first_name, last_name) VALUES (?,?,?)";
    public static final String INSERT_STUDENT_ON_COURSE = "INSERT INTO students.students_courses (student_id, group_id, first_name, last_name, course_id, course_name, course_description) VALUES (?,?,?,?,?,?,?)";
    public static final String DELETE_STUDENT_BY_ID = "DELETE FROM students.students WHERE student_id = '%d'";
    public static final String DELETE_STUDENT_FROM_COURSE = "DELETE FROM students.students_courses WHERE student_id = '%d' AND course_id = '%d'";

    public StudentDaoImpl(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void removeStudentById(int studentID) throws SQLException, IOException {
        try (Connection connection = connectionHandler.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format(DELETE_STUDENT_BY_ID, studentID));
        }
    }

    public Optional<Student> getStudentById(int studentID) throws SQLException, IOException {
        try (Connection connection = connectionHandler.getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet students = statement.executeQuery(String.format(SELECT_STUDENTS_WHERE_STUDENT_ID, studentID ));
            Optional<ResultSet> resultSet = Optional.of(students);
            if (resultSet.isPresent()) {
                Student student = null;
                while (students.next()) {
                    int idStudent = students.getInt("student_id");
                    int groupID = students.getInt("group_id");
                    String studentFirstName = students.getString("first_name");
                    String studentLastName = students.getString("last_name");
                    student = new Student(idStudent, groupID, studentFirstName, studentLastName);
                }
                return Optional.of(student);
            }
            else {
                return Optional.empty();
            }
        }
    }

    public List<Student> getStudentsByGroupId(int groupId) throws SQLException, IOException {
        List<Student> studentsInGroup = new ArrayList<>();
        try (Connection connection = connectionHandler.getConnection()){
                Statement statement = connection.createStatement();
                ResultSet students = statement.executeQuery(String.format(SELECT_STUDENTS_WHERE_GROUP_ID, groupId));
                while (students.next()) {
                    int studentID = students.getInt("student_id");
                    int groupID = students.getInt("group_id");
                    String studentFirstName = students.getString("first_name");
                    String studentLastName = students.getString("last_name");
                    studentsInGroup.add(new Student(studentID, groupID, studentFirstName, studentLastName));
                }
        }
        return studentsInGroup;
    }

    public void assignStudentOnCourse(Student student, Course course) throws SQLException, IOException {
        try (Connection connection = connectionHandler.getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_STUDENT_ON_COURSE);
            insertStatement.setInt(1, student.getStudentID());
            insertStatement.setInt(2, student.getGroupID());
            insertStatement.setString(3, student.getFirstName());
            insertStatement.setString(4, student.getLastName());
            insertStatement.setInt(5, course.getId());
            insertStatement.setString(6, course.getCourseName());
            insertStatement.setString(7, course.getCourseDescription());
            insertStatement.executeUpdate();
        }
    }

    public List<Student> getStudentsByCourseId(int courseId) throws SQLException, IOException {
        List<Student> studentsOnCourse = new ArrayList<>();
        try (Connection connection = connectionHandler.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet students = statement.executeQuery(String.format(SELECT_STUDENTS_WHERE_COURSE_ID, courseId));
            while (students.next()) {
                int studentID = students.getInt("student_id");
                int groupID = students.getInt("group_id");
                String firstName = students.getString("first_name");
                String lastName = students.getString("last_name");
                studentsOnCourse.add(new Student(studentID, groupID, firstName, lastName));
            }
        }
        return studentsOnCourse;
    }

    public void deleteStudentFromCourse(int studentId, int courseId) throws SQLException, IOException {
        try (Connection connection = connectionHandler.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format(DELETE_STUDENT_FROM_COURSE, studentId, courseId));
        }
    }

    public void addStudent(Student student) throws SQLException, IOException {
        try (Connection connection = connectionHandler.getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_STUDENT);
            insertStatement.setInt(1, student.getGroupID());
            insertStatement.setString(2, student.getFirstName());
            insertStatement.setString(3, student.getLastName());
            insertStatement.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException, IOException {
        List<Student> studentsInUniversity = new ArrayList<>();
        try (Connection connection = connectionHandler.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet students = statement.executeQuery(SELECT_ALL_STUDENTS);
            while (students.next()) {
                int studentID = students.getInt("student_id");
                int groupID = students.getInt("group_id");
                String studentFirstName = students.getString("first_name");
                String studentLastName = students.getString("last_name");
                studentsInUniversity.add(new Student(studentID, groupID, studentFirstName, studentLastName));
            }
        }
        return studentsInUniversity;
    }
}
