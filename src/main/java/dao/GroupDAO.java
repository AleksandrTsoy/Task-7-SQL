package dao;

import entity.Group;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface GroupDAO {
    void addGroup(Group group) throws SQLException, IOException;
    List<Group> getAllGroups() throws SQLException, IOException;
}
