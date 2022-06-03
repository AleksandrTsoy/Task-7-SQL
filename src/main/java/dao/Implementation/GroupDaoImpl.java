package dao.Implementation;

import dao.ConnectionHandler;
import dao.GroupDAO;
import entity.Group;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl implements GroupDAO {
    private ConnectionHandler connectionHandler;
    public static final String SELECT_ALL_GROUPS = "SELECT * FROM students.groups";
    public static final String INSERT_GROUP = "INSERT INTO students.groups (group_name) VALUES (?)";

    public GroupDaoImpl(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void addGroup(Group group) throws SQLException, IOException {
        try (Connection connection = connectionHandler.getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_GROUP);
            insertStatement.setString(1, group.getGroupName());
            insertStatement.executeUpdate();
        }
    }

    public List<Group> getAllGroups() throws SQLException, IOException {
        List<Group> groupsList = new ArrayList<>();
        try (Connection connection = connectionHandler.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet groupsAdd = statement.executeQuery(SELECT_ALL_GROUPS);
            while (groupsAdd.next()) {
                int groupID = groupsAdd.getInt("group_id");
                String groupsName = groupsAdd.getString("group_name");
                groupsList.add(new Group(groupID, groupsName));
            }
        }
        return groupsList;
    }
}
