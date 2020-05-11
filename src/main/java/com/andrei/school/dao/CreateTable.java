package com.andrei.school.dao;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class CreateTable {

    public void createTable() throws IOException, SQLException {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        Connection connection = connectionProvider.getConnection();

        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new FileReader(
                Objects.requireNonNull(CreateTable.class.getClassLoader().getResource("schema.sql")).getFile()
        ));
        sr.runScript(reader);
    }
}
