package daoTest;

import generator.DataGenerator;
import entity.Student;
import util.FileReader;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentDaoImplTest {
    private static final String FIRST_NAME = "Donald";
    private static final String LAST_NAME = "Tramp";
    private static final String CREATE_SCHEMA = "CREATE SCHEMA students";
    private static final String INSERT_INTO_TABLE_STUDENTS = "INSERT INTO students.students (group_id, first_name, last_name) VALUES (?,?,?)";
    private static final String DELETE_FROM_TABLE_STUDENTS = "DELETE FROM students.students WHERE student_id = '1'";
    private static final String SELECT_FROM_STUDENTS = "SELECT * FROM students.students";
    private JdbcDataSource dataSource = new JdbcDataSource();
    private Connection connection;

    @BeforeEach
    void init() throws SQLException, IOException, URISyntaxException {
        dataSource.setURL("jdbc:h2:mem:dbname;DB_CLOSE_DELAY=-1");
        dataSource.setUser("Aleksandr_Tsoy");
        dataSource.setPassword("1234");
        connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(CREATE_SCHEMA);
        statement.executeUpdate(new FileReader().readFile("CreateTable.sql"));
        List<Student> students = new DataGenerator(new FileReader()).createStudents();
        for (Student student : students) {
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_INTO_TABLE_STUDENTS);
            insertStatement.setInt(1, student.getGroupID());
            insertStatement.setString(2, student.getFirstName());
            insertStatement.setString(3, student.getLastName());
            insertStatement.executeUpdate();
        }
    }

    @Test
    public void testAddStudent() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet beforeAddStudent = statement.executeQuery(SELECT_FROM_STUDENTS);
        int countBefore = 0;
        while (beforeAddStudent.next()) {
            countBefore++;
        }
        PreparedStatement insertStatement = connection.prepareStatement(INSERT_INTO_TABLE_STUDENTS);
        int randomGroupID = (int) (Math.random() * ((9) + 1));
        insertStatement.setInt(1, randomGroupID);
        insertStatement.setString(2, FIRST_NAME);
        insertStatement.setString(3, LAST_NAME);
        insertStatement.executeUpdate();
        ResultSet afterAddStudent = statement.executeQuery(SELECT_FROM_STUDENTS);
        int countAfter = 0;
        while (afterAddStudent.next()) {
            countAfter++;
        }
        assertEquals(countAfter - countBefore, 1);
    }

    @Test
    public void testRemoveStudent() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet beforeRemoveStudent = statement.executeQuery(SELECT_FROM_STUDENTS);
        int countBefore = 0;
        while (beforeRemoveStudent.next()) {
            countBefore++;
        }
        statement.executeUpdate(DELETE_FROM_TABLE_STUDENTS);
        ResultSet afterRemoveStudent = statement.executeQuery(SELECT_FROM_STUDENTS);
        int countAfter = 0;
        while (afterRemoveStudent.next()) {
            countAfter++;
        }
        assertEquals(countBefore - countAfter, 1);
    }
}
