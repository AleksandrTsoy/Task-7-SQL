package entity;

public class Group {
    private int id;
    private String groupName;

    public Group(int groupID, String groupName) {
        this.id = groupID;
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }
}
