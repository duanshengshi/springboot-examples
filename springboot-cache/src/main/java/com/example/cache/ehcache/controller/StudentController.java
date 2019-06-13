package com.example.cache.ehcache.controller;

import com.example.cache.common.model.Student;
import com.example.cache.common.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("getStudents")
    public Object getStudents(){
        List<Student> students = studentService.getStudents();
        if(students!=null&&!students.isEmpty()){
            return students;
        }
        return "nothing";
    }

}
