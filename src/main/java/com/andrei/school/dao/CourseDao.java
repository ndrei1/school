package com.andrei.school.dao;

import com.andrei.school.model.Course;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    private static final String ADD_COURSE_FROM_STUDENT = "insert into courses_students (student_id, courses_name)VALUES(?,?)";
    private static final String CREATE = "insert into courses(name, description) VALUES (?,?)";
    private static final String RETURN_LIST_WITH_UNIQUE_COURSES = "select courses_students.courses_name from courses_students where student_id = ?";
    private static final String DELETE_COURSE_FROM_STUDENT = "delete\n" +
            "from courses_students\n" +
            "where student_id = ?\n" +
            "  and courses_name = ?;";
    private static final String GET_ALL_NAME_FROM_ONE_COURSE = "select courses_students.student_id from courses_students where courses_name = ?";
    private static final String NAME_RETURN = "select students.name,surname from public.students where id =?";

    public CourseDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    private ConnectionProvider connectionProvider;

    public void addCourseFromStudent(int idStudent, String nameOfCourse) throws IOException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_COURSE_FROM_STUDENT);
            preparedStatement.setInt(1, idStudent);
            preparedStatement.setString(2, nameOfCourse);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Course course) throws IOException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> returnListWithUniqueCourses(int id) throws IOException {
        List<String> listWithUniqueCourses = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(RETURN_LIST_WITH_UNIQUE_COURSES);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    listWithUniqueCourses.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listWithUniqueCourses;
    }

    public void deleteCourseFromStudent(int studentId, String courseFromDelete) throws IOException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COURSE_FROM_STUDENT);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, courseFromDelete);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllNameFromOneCourse(String nameOfCourse) throws IOException {
        List<String> studentsByOneCourse = new ArrayList<>();
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_NAME_FROM_ONE_COURSE);
            preparedStatement.setString(1, nameOfCourse);
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    studentsByOneCourse.add(nameReturn(resultSet.getInt(1)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentsByOneCourse;
    }

    private String nameReturn(int idStudent) throws IOException, SQLException {
        try (Connection connection = connectionProvider.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(NAME_RETURN);
            preparedStatement.setInt(1, idStudent);
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getString(1) + " " + resultSet.getString(2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
