package dao;

import entity.Course;
import entity.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StudentDAO {
    Optional<Student> getStudentById(int studentID) throws SQLException, IOException;
    List<Student> getStudentsByCourseId(int courseId) throws SQLException, IOException;
    List<Student> getStudentsByGroupId(int groupId) throws SQLException, IOException;
    List<Student> getAllStudents() throws SQLException, IOException;
    void addStudent(Student student) throws SQLException, IOException;
    void removeStudentById(int studentID) throws SQLException, IOException;
    void assignStudentOnCourse(Student student, Course course) throws SQLException, IOException;
    void deleteStudentFromCourse(int studentId, int courseId) throws SQLException, IOException;

}
