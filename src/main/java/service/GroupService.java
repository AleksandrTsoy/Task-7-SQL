package service;

import dao.GroupDAO;
import dao.Implementation.GroupDaoImpl;
import dao.StudentDAO;
import entity.Group;
import util.DataInput;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupService {
    GroupDAO groupsDAO;
    StudentDAO studentDAO;
    DataInput userInput;

    public GroupService(GroupDaoImpl groupsDAO, StudentDAO studentDAO, DataInput userInput) {
        this.groupsDAO = groupsDAO;
        this.studentDAO = studentDAO;
        this.userInput = userInput;
    }

    public void addGroup(Group group) throws SQLException, IOException {
        groupsDAO.addGroup(group);
        System.out.printf("%d. %s \n", group.getId(), group.getGroupName());
    }

    public void groupsWhereSameCountStudents() throws SQLException, IOException {
        System.out.print("\nInput count: ");
        int count = Integer.parseInt(userInput.inputData());
        List<Integer> groupMax = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            int countStudents = studentDAO.getStudentsByGroupId(i).size();
            if (countStudents <= count) {
                groupMax.add(i);
            }
        }
        System.out.println("Groups where same or less " + count + " students:");
        groupMax.forEach((p) -> System.out.printf("Group %d.\n", p));
    }
}
