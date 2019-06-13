package com.example.cache.common.service;

import com.example.cache.common.dao.StudentMapper;
import com.example.cache.common.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public List<Student> getStudents(){
        return studentMapper.getStudents();
    }
}
