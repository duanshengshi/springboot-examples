package com.example.cache.redis.service;

import com.example.cache.common.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectServiceTest {
    @Autowired
    private ObjectService objectService;

    @Test
    public void getStudentFromRedis() {
        Student student = objectService.getStudentFromRedis("dss");
        System.out.println(student.toString());
    }

    @Test
    public void setStudentToRedis() {
        Student student = new Student();
        student.setId(1);
        student.setName("dss");
        student.setMobile("123456");
        student.setSex("man");
        boolean res = objectService.setStudentToRedis(student);
        System.out.println(res);
    }
}