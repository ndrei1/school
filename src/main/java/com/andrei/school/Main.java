package com.andrei.school;

import com.andrei.school.dao.*;
import com.andrei.school.model.Course;
import com.andrei.school.model.Group;
import com.andrei.school.model.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        DataGenerate dataGenerate = new DataGenerate();
        DataBase dataBase = new DataBase();
        List<Student> students = dataGenerate.generateName();
        List<Course> courses = dataGenerate.generateCourses();
        List<Group> groups = dataGenerate.generateGroup();
        dataBase.createDataBase(students, courses, groups);
        while (true) {
            if (!startMenu(courses)) {
                break;
            }
        }
    }

    private static Boolean startMenu(List<Course> courses) throws IOException {
        ConnectionProvider connectionProvider = new ConnectionProvider();
        StudentDao studentDao = new StudentDao(connectionProvider);
        CourseDao courseDao = new CourseDao(connectionProvider);
        GroupDao groupDao = new GroupDao(connectionProvider);

        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Find all groups with less or equal students" +
                System.lineSeparator() +
                "2. Find all students related to the course with the specified name." +
                System.lineSeparator() +
                "3. Add New Student" +
                System.lineSeparator() +
                "4. Delete student by STUDENT_ID" +
                System.lineSeparator() +
                "5. Add a student to the course (from the list)" +
                System.lineSeparator() +
                "6. Remove student from one of his courses");

        switch (scanner.nextInt()) {
            case 1:
                System.out.println("Enter quantity of student");
                for (String s : groupDao.findAllGroupWithLessOrEqualsStudents(scanner.nextInt())) {
                    System.out.println(s);
                }
                break;

            case 2:
                System.out.println("Choose one course from list");
                for (Course listWithCours : courses) {
                    System.out.println(listWithCours.getName());
                }
                for (String s : courseDao.getAllNameFromOneCourse(scanner.next())) {
                    System.out.println(s);
                }
                break;

            case 3:
                System.out.println("Enter name of new student");
                String name = scanner.next();
                System.out.println("Enter surname of new student");
                String surName = scanner.next();
                System.out.println("Enter id from with group relate new student");
                int idGroup = scanner.nextInt();
                studentDao.addStudent(name, surName, idGroup);
                break;
            case 4:
                System.out.println("Enter id student whom should be delete");
                studentDao.deleteStudent(scanner.nextInt());
                break;
            case 5:
                System.out.println("Enter the student id you want to add a new course from 1 to 200");
                int studentId = scanner.nextInt();
                System.out.println("This student has courses ");
                for (String s : courseDao.returnListWithUniqueCourses(studentId)) {
                    System.out.println(s);
                }
                System.out.println("Enter the course name from the list provided.");
                for (Course listWithCours : courses) {
                    System.out.println(listWithCours.getName());
                }
                courseDao.addCourseFromStudent(studentId, scanner.next());

                break;
            case 6:
                System.out.println("Enter the id of the student whose course you want to delete");
                System.out.println("Courses possible to delete");
                int idStudent = scanner.nextInt();
                for (String s : courseDao.returnListWithUniqueCourses(idStudent)) {
                    System.out.print(s + " ,");
                }
                System.out.println("Select a course to delete ");
                courseDao.deleteCourseFromStudent(idStudent, scanner.next());
                break;
            default:
                System.out.println("Menu dont has this link");
                return true;

        }
        while (true) {
            System.out.println("Continue yes or no?");
            String choose = scanner.next();
            if (choose.equals("no")) {
                return false;
            }
            if (choose.equals("yes")) {
                return true;
            }
        }
    }
}
