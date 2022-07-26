package com.example.myapplication.lcyedu.bean;

import com.google.gson.annotations.SerializedName;

public class CourseViewRequest {

    @SerializedName("courseId")
    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
