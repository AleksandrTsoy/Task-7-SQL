package service;

import dao.CourseDAO;
import dao.Implementation.CourseDaoImpl;
import entity.Course;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class CourseService {
    private CourseDAO courseDAO;
    public CourseService(CourseDaoImpl courseDAO) {
        this.courseDAO = courseDAO;
    }

    public void addCourse(Course course) throws SQLException, IOException, URISyntaxException {
        courseDAO.addCourse(course);
        System.out.printf("%d. %s - %s. \n",
                course.getId(),
                course.getCourseName(),
                course.getCourseDescription());
    }


}
