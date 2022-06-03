package generator;

import entity.Course;
import entity.Group;
import entity.Student;
import util.FileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class DataGenerator {
    private FileReader fileReader;

    public DataGenerator(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public List<Course> createCourses() throws IOException, URISyntaxException {
        List<Course> coursesList = new ArrayList<>();
        String textFromFile = fileReader.readFile("Courses.txt");
        Map<String, List<String>> coursesMap = textFromFile.lines().collect(toMap(
                (p) -> p.substring(0,1),
                (p) -> Arrays.stream(p.substring(2).split("_")).toList()));
        coursesMap.forEach((key,value) -> coursesList.add(new Course(Integer.parseInt(value.get(0)), value.get(1), value.get(2))));
        return coursesList;
    }

    public List<Group> createGroups() {
        List<Group> groupsList = new ArrayList<>();
        Random random = new Random();
        int count = 0;
        for (int i = 0; i < 10; i++) {
            String groupChar = random.ints(65, 90)
                    .limit(2)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            String groupInt = random.ints(48, 57)
                    .limit(2)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            groupsList.add(new Group(++count, groupChar + "-" + groupInt));
        }
        return groupsList;
    }

    public List<Student> createStudents() throws IOException, URISyntaxException {
        List<Student> studentsList = new ArrayList<>();
        int groupID = 0;
        String firstNames = fileReader.readFile("FirstName.txt");
        String lastNames = fileReader.readFile("LastName.txt");
        List<String> firstNameList = firstNames.lines().collect(Collectors.toList());
        List<String> lastNameList = lastNames.lines().collect(Collectors.toList());
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            if (i < 20) {groupID = 1;}
            if (i >= 20 && i < 40) {groupID = 2;}
            if (i >= 40 && i < 60) {groupID = 3;}
            if (i >= 60 && i < 80) {groupID = 4;}
            if (i >= 80 && i < 100) {groupID = 5;}
            if (i >= 100 && i < 120) {groupID = 6;}
            if (i >= 120 && i < 140) {groupID = 7;}
            if (i >= 140 && i < 160) {groupID = 8;}
            if (i >= 160 && i < 180) {groupID = 9;}
            if (i >= 180) {groupID = 10;}
            int nameRandomIndex = random.nextInt(firstNameList.size());
            String firstName = firstNameList.get(nameRandomIndex);
            int surnameRandomIndex = random.nextInt(lastNameList.size());
            String lastName = lastNameList.get(surnameRandomIndex);
            studentsList.add(new Student(i+1, groupID, firstName, lastName));
        }
        return studentsList;
    }
}
