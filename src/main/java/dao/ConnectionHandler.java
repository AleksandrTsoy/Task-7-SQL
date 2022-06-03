package dao;

import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionHandler {
    private final PGSimpleDataSource dataSource = new PGSimpleDataSource();
    private static final String PATH_TO_PROPERTIES = "Connection.properties";

    public ConnectionHandler(Properties properties) throws IOException {
        properties.load(ConnectionHandler.class.getClassLoader().getResourceAsStream(PATH_TO_PROPERTIES));
        dataSource.setDatabaseName(properties.getProperty("databaseName"));
        dataSource.setUser(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
