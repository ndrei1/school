package com.andrei.school.dao;

import com.andrei.school.model.Course;
import com.andrei.school.model.Group;
import com.andrei.school.model.Student;

import java.util.*;

public class DataGenerate {

    public List<Group> generateGroup() {
        List<Group> listWithGroup = new ArrayList<Group>();
        String str = "QWERTYUIOPASDFGHJKLZXCVBNM";
        Random random = new Random();
        StringBuilder nameGroup = new StringBuilder();

        for (int j = 0; j < 10; j++) {

            for (int i = 0; i < 2; i++) {
                char ch = str.charAt(random.nextInt(str.length()));
                nameGroup.append(ch);
            }
            nameGroup.append('-');
            for (int i = 0; i < 2; i++) {
                int a = (int) (Math.random() * 10);
                nameGroup.append(a);
            }
            listWithGroup.add(new Group(j, nameGroup.toString()));
            nameGroup.setLength(0);
        }
        return listWithGroup;
    }


    public List<Student> generateName() {
        List<Student> students = new ArrayList<Student>();
        List<String> listWithSurname = Arrays.asList("Романенко", "Горобчук", "Гурьев", "Горшков", "Буров", "Воронов",
                "Иваненко", "Силин", "Григорьев", "Васильев", "Моисеенко", "Дорофеев", "Куликов", "Кабанов", "Щербаков",
                "Ларионов", "Дидовец", "Лукашенко", "Фокин", "Головин");

        List<String> listWithName = Arrays.asList("Иосий", "Чеслав", "Евдокий", "Харитон", "Камил", "Любомир",
                "Герман", "Динар", "Устин", "Стефан", "Андрей", "Максим", "Володя", "Вова", "Артем",
                "Кирилл", "Антон", "Дмитрий", "Геннадий", "Евгений");

        for (int j = 0; j < 200; j++) {
            String surname = listWithSurname.get((int) (Math.random() * 20));
            String name = listWithName.get((int) (Math.random() * 20));
            students.add(new Student(j, name, surname, 1));
        }
        return students;
    }

    public List<Course> generateCourses() {
        List<Course> listWithCourses = new ArrayList<Course>();
        listWithCourses.add(new Course(1, "ancient history", "about the story in our world"));
        listWithCourses.add(new Course(2, "music", "students play some musical instrument"));
        listWithCourses.add(new Course(3, "philosophy", "explore humans mind"));
        listWithCourses.add(new Course(4, "art history", "students draw some interesting and beautiful pictures"));
        listWithCourses.add(new Course(5, "math", "explore math rule"));
        listWithCourses.add(new Course(6, "geography", "explore town and there capital"));
        listWithCourses.add(new Course(7, "sociology", "explore humans life in society"));
        listWithCourses.add(new Course(8, "zoology", "learn hou to correct live with animals"));
        listWithCourses.add(new Course(9, "genetics", "explore humans genetic code"));
        listWithCourses.add(new Course(10, "swim", "students swim in swimming pool"));
        return listWithCourses;
    }
}

