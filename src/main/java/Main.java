import dao.ConnectionHandler;
import dao.Implementation.CourseDaoImpl;
import dao.Implementation.GroupDaoImpl;
import dao.Implementation.StudentDaoImpl;
import dao.StudentDAO;
import controller.MenuController;
import entity.Course;
import entity.Group;
import entity.Student;
import generator.DataBaseInitializer;
import generator.DataGenerator;
import service.CourseService;
import service.GroupService;
import service.StudentService;
import util.FileReader;
import util.DataInputImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, URISyntaxException {
        FileReader fileReader = new FileReader();
        DataGenerator dataGenerator = new DataGenerator(fileReader);
        Properties property = new Properties();
        ConnectionHandler connectionHandler = new ConnectionHandler(property);
        DataInputImpl userInput = new DataInputImpl();
        DataBaseInitializer createTable = new DataBaseInitializer(fileReader, connectionHandler);
        MenuController menu = new MenuController();
        createTable.createTables();

        StudentDAO studentDAO = new StudentDaoImpl(connectionHandler);
        GroupDaoImpl groupsDAO = new GroupDaoImpl(connectionHandler);
        GroupService groupsService = new GroupService(groupsDAO, studentDAO, userInput);
        System.out.println("University groups:");
        for (Group group : dataGenerator.createGroups()) {
            groupsService.addGroup(group);
        }

        CourseDaoImpl courseDAO = new CourseDaoImpl(connectionHandler);
        CourseService coursesService = new CourseService(courseDAO);
        System.out.println("\nWe have many courses:");
        for (Course course : dataGenerator.createCourses()) {
            coursesService.addCourse(course);
        }

        StudentService studentsService = new StudentService(studentDAO, courseDAO, userInput, fileReader);
        System.out.println("\nOur students:");
        for (Student student : dataGenerator.createStudents()) {
            studentsService.addStudent(student);
        }
        studentsService.addCoursesToStudents();

        menu.getMenu();
        String item = "";
        while (!(item = userInput.inputData()).equals("stop")) {
            switch (item) {
                case "a":
                    groupsService.groupsWhereSameCountStudents();
                    break;
                case "b":
                    studentsService.studentsRelatedToCourse();
                    break;
                case "c":
                    studentsService.addNewStudent();
                    break;
                case "d":
                    studentsService.removeStudent();
                    break;
                case "e":
                    studentsService.addStudentOnCourse();
                    break;
                case "f":
                    studentsService.removeStudentFromCourse();
                    break;
                default:
                    System.out.println("error");
                    break;
            }
        }
    }
}
