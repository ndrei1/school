package com.andrei.school.dao;

import com.andrei.school.model.Group;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {

    private ConnectionProvider connectionProvider;

    private static final String CREATE = "insert into groups(name) VALUES (?)";
    private static final String FIND_ALL_GROUP_WITH_LESS_OR_EQUALS_STUDENTS = "select students.idgroup, count(*)\n" +
            "from students\n" +
            "group by idgroup\n" +
            "having count(*) >= ?";
    private static final String NAME_RETURN = "select groups.name from public.groups where id = ?";

    public GroupDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void create(Group group) throws IOException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, group.getName());
            preparedStatement.execute();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> findAllGroupWithLessOrEqualsStudents(int quantityOfStudent) throws IOException {
        List<String> groups = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_GROUP_WITH_LESS_OR_EQUALS_STUDENTS);
            preparedStatement.setInt(1, quantityOfStudent);
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder group = new StringBuilder();
                while (resultSet.next()) {
                    group.append(nameReturn(resultSet.getInt(1))).append("-");
                    group.append(resultSet.getString(2));
                    groups.add(group.toString());
                    group = new StringBuilder();
                }
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    private String nameReturn(int idCourses) throws IOException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(NAME_RETURN);
            preparedStatement.setInt(1, idCourses);
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
