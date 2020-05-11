package com.andrei.school.dao;

import com.andrei.school.model.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDao {

    private ConnectionProvider connectionProvider;

    private static final String ADD_STUDENT = "insert into students (name, surname, idgroup) values(?,?,?)";
    private static final String DELETE_STUDENT = "delete from students where id = ? ";
    private static final String CREATE = "insert into students(name, surname, idgroup) VALUES (?,?,?)";

    public StudentDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void addStudent(String name, String surName, int groupId) throws IOException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_STUDENT);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surName);
            preparedStatement.setInt(3, groupId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int idStudent) throws IOException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT);
            preparedStatement.setInt(1, idStudent);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Student student) throws IOException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSurname());
            preparedStatement.setInt(3, student.getGroupId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
