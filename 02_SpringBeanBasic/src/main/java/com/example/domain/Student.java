package com.example.domain;

public class Student {

    public Student() {
        System.out.println("Student: Constructor");
    }

    // Student 依赖 Classroom 实例
    public void setClassroom(Classroom classroom) {
        System.out.println("Student: setClassroom(): classroom=" + classroom);
    }

}
