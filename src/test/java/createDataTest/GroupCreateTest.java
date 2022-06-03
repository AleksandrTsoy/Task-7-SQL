package createDataTest;

import generator.DataGenerator;
import entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.FileReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupCreateTest {
    DataGenerator dataGenerator;

    @BeforeEach
    public void init() {
        dataGenerator = new DataGenerator(new FileReader());
    }

    @Test
    public void testGroupCreate() {
        List<Group> groups = dataGenerator.createGroups();
        for (Group group : groups) {
            boolean result = group.getGroupName().matches("[A-Z]{2}" + "-" + "\\d{2}");
            assertTrue(result);
        }
    }
}
