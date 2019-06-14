package com.example.cache.common.dao;

import com.example.cache.common.model.Student;

import java.util.List;

public interface StudentMapper {
    List<Student> getStudents();

    public void addStudent(Student student);
}
