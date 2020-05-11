package com.andrei.school.model;

public class Group {

    private int groupId;
    private String name;

    public Group(int groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }
}
