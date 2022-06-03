package fileReaderTest;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileReaderTest {
    private final String SCRIPT = "DROP TABLE IF EXISTS students.students_courses;\r\n" +
            "DROP TABLE IF EXISTS students.courses;\r\n" +
            "DROP TABLE IF EXISTS students.groups;\r\n" +
            "DROP TABLE IF EXISTS students.students;\r\n" +
            "\r\n" +
            "CREATE TABLE students.courses (\r\n" +
            "    course_id SERIAL UNIQUE,\r\n" +
            "    course_name character varying(30) UNIQUE,\r\n" +
            "    course_description character varying(30)\r\n" +
            ");\r\n" +
            "\r\n" +
            "CREATE TABLE students.groups (\r\n" +
            "    group_id SERIAL UNIQUE,\r\n" +
            "    group_name character varying(30)\r\n" +
            ");\r\n" +
            "\r\n" +
            "CREATE TABLE students.students (\r\n" +
            "    student_id SERIAL UNIQUE,\r\n" +
            "    group_id integer,\r\n" +
            "    first_name character varying(30),\r\n" +
            "    last_name character varying(30),\r\n" +
            "    CONSTRAINT students_pkey PRIMARY KEY (student_id)\r\n" +
            ");\r\n" +
            "\r\n" +
            "CREATE TABLE students.students_courses (\r\n" +
            "    course_id INTEGER,\r\n" +
            "    course_name character varying(30),\r\n" +
            "    course_description character varying(30),\r\n" +
            "    student_id INTEGER,\r\n" +
            "    group_id integer,\r\n" +
            "    first_name character varying(30),\r\n" +
            "    last_name character varying(30),\r\n" +
            "    FOREIGN KEY (course_id) REFERENCES students.courses (course_id) ON DELETE CASCADE,\r\n" +
            "    FOREIGN KEY (student_id) REFERENCES students.students (student_id) ON DELETE CASCADE\r\n" +
            ");";
    @Test
    public void fileReaderTest() throws IOException, URISyntaxException {
        String actual = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("CreateTable.sql").toURI()));
        String expexted = SCRIPT;
        assertEquals(expexted, actual);
    }
}
