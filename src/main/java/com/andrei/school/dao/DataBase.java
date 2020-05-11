package com.andrei.school.dao;

import com.andrei.school.model.Course;
import com.andrei.school.model.Group;
import com.andrei.school.model.Student;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static final String SQL_DROP = "drop table courses_students;\n" +
            "drop table courses;\n" +
            "drop table students;\n" +
            "drop table groups;";

    private static final String CREATE_TABLE_MANY_MANY = "insert into courses_students (student_id, courses_name) values(?,?)";

    private void addStudentsToTable(List<Student> students, List<Group> listWithGroup) throws IOException {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        StudentDao studentDao = new StudentDao(connectionProvider);
        int randomBreak = (int) (1 + Math.random() * 9);
        int quantityOfStudent = 1;
        for (int i = 0; i < 200; i++) {
            if (quantityOfStudent >= randomBreak) {
                randomBreak = (int) (1 + Math.random() * 9);
                quantityOfStudent = 0;
            }
            studentDao.create(new Student(i, students.get(i).getName(),
                    students.get(i).getSurname(),
                    listWithGroup.get(randomBreak).getGroupId()));
            quantityOfStudent++;
        }
    }

    public void createDataBase(List<Student> students,List<Course> courses,List<Group> groups) throws SQLException, IOException {
        dropAllTable();
        CreateTable create = new CreateTable();
        create.createTable();
        createTableWithGroup(groups);
        addStudentsToTable(students,groups);
        addCoursesToTable(courses);
        addCoursesToStudent(courses);
    }

    private void dropAllTable() throws IOException {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        try (Connection connection = connectionProvider.getConnection()) {
            String sql = SQL_DROP;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {

        }

    }

    private void createTableWithGroup(List<Group> group) throws SQLException, IOException {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        GroupDao groupDao = new GroupDao(connectionProvider);
        for (int i = 0; i < 10; i++) {
            groupDao.create(new Group(i, group.get(i).getName()));
        }
    }

    private void addCoursesToTable(List<Course> courses) throws IOException {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        CourseDao courseDao = new CourseDao(connectionProvider);
        for (int i = 0; i < courses.size(); i++) {
            courseDao.create(new Course(i, courses.get(i).getName(), courses.get(i).getDescription()));
        }
    }


    private void addCoursesToStudent(List<Course> listWithCourses) throws IOException {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        try (Connection connection = connectionProvider.getConnection()) {
            String INSERT = CREATE_TABLE_MANY_MANY;
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            List<Integer> uniqueCourses = new ArrayList<>();
            for (int i = 1; i <= 200; i++) {
                while (true) {
                    generateRandomCourses(uniqueCourses);
                    if (uniqueCourses.get(0).equals(uniqueCourses.get(1)) || uniqueCourses.get(0).equals(uniqueCourses.get(2)) || uniqueCourses.get(1).equals(uniqueCourses.get(2))) {
                        uniqueCourses.clear();
                    } else break;
                }
                for (int j = 1; j <= 3; j++) {
                    preparedStatement.setInt(1, i);
                    preparedStatement.setString(2, listWithCourses.get(uniqueCourses.get(j - 1)).getName());
                    preparedStatement.execute();
                }
                uniqueCourses.clear();
            }
        } catch (SQLException e) {

        }
    }

    private void generateRandomCourses(List<Integer> uniqueCourses) {
        for (int i = 0; i < 3; i++) {
            uniqueCourses.add((int) (1 + Math.random() * 9));
        }
    }
}
