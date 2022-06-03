package dao;

import entity.Course;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CourseDAO {
    List<Course> getCourses() throws SQLException, IOException;
    Optional<Course> getCourseByName(String courseName) throws SQLException, IOException;
    List<Course> getCoursesByStudentId(int studentId) throws SQLException, IOException;
    void addCourse(Course course) throws SQLException, IOException;

}
