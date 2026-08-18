package com.apt.academic;

import java.util.ArrayList;

public class Semester {
    ArrayList<Course> courses;

    public Course getCourse(String code) {
        for (Course c : courses) {
            if (c.getCode() == code) {
                return c;
            }
        }
        return null;
    }

    public String[] getCourseList() {
        String[] courseList = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            courseList[i] = courses.get(i).getCode();
        }
        return courseList;
    }
}