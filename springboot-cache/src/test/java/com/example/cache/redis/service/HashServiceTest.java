package com.example.cache.redis.service;

import com.example.cache.common.model.Student;
import com.example.cache.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HashServiceTest {

    @Autowired
    private HashService hashService;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void setHash() {
        Student student = new Student();
        student.setId(1);
        student.setName("dss");
        student.setMobile("123456");
        student.setSex("man");
        hashService.setHash(student);
    }

    @Test
    public void getHash() {
        Student student = hashService.getHash("dss");
        System.out.println(student);
        boolean b = redisUtil.hset("dss","name","dss");
        System.out.println(b);
        student = hashService.getHash("dss");
        System.out.println(student);
    }
}