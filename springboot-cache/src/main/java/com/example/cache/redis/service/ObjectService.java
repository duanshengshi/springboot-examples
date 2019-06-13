package com.example.cache.redis.service;

import com.example.cache.common.annotation.RedisCacheable;
import com.example.cache.common.model.Student;
import com.example.cache.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjectService {

    @Autowired
    private RedisUtil redisUtil;

    @RedisCacheable(key = "#name")
    public Student getStudentFromRedis(String name){
        Student student = (Student) redisUtil.get(name);
        return student;
    }

    public boolean setStudentToRedis(Student student){
        return redisUtil.set(student.getName(),student);
    }

}
