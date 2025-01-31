package com.example.domain;

public class Classroom {

    public Classroom() {
        System.out.println("Classroom: constructor");
    }

    // Classroom 依赖 Student 实例
    public void setStudent(Student student) {
        System.out.println("Classroom: setStudent(), student=" + student);
    }

}
