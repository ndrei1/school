package com.andrei.school.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class ConnectionProvider {

    private String getProperties(String propertiesKey) throws IOException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream(
                Objects.requireNonNull(getClass().getClassLoader().getResource("config.properties")).getFile()
        );
        properties.load(in);
        return (String) properties.get(propertiesKey);
    }

    public Connection getConnection() throws SQLException, IOException {
        return  DriverManager.getConnection(getProperties("url"),
                getProperties("user"),
                getProperties("password"));
    }

}
