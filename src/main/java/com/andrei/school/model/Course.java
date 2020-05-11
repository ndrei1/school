package com.andrei.school.model;

public class Course {

    private int courseId;
    private String name;
    private String description;

    public Course(int courseId, String name, String description) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
