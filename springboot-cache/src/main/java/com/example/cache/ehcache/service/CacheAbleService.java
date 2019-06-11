package com.example.cache.ehcache.service;


import com.example.cache.common.model.Student;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheAbleService {

    @Cacheable(value = "cache1",key = "#name")
    public Student getStudentByName(String name, String sex, int id, String mobile){
        Student student = new Student();
        student.setName(name);
        student.setSex(sex);
        student.setMobile(mobile);
        student.setId(id);
        return student;
    }

    @Cacheable(value = "demoCache",key = "#name")
    public Student getStudentFromEHCache(String name){
        Student student = new Student();
        student.setName(name);
        student.setSex("woman");
        student.setMobile("654321");
        student.setId(2);
        return student;
    }
}
