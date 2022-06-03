package generator;

import dao.ConnectionHandler;
import util.FileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;

public class DataBaseInitializer {
    private FileReader fileReader;
    private ConnectionHandler connectionHandler;
    public DataBaseInitializer(FileReader fileReader, ConnectionHandler connectionHandler) {
        this.fileReader = fileReader;
        this.connectionHandler = connectionHandler;
    }
    public void createTables() {
        try (Connection connection = connectionHandler.getConnection()) {
            Statement statement = connection.createStatement();
            String textFromFile = fileReader.readFile("CreateTable.sql");
            statement.executeUpdate(textFromFile);
            statement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
