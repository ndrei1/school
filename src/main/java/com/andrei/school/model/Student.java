package com.andrei.school.model;

public class Student {

    private int id;
    private String name;
    private String surname;
    private int groupId;


    public Student(int id, String name, String surname, int groupId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.groupId = groupId;

    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getId() {
        return id;
    }
}
