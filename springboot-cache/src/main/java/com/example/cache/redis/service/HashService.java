package com.example.cache.redis.service;

import com.example.cache.common.model.Student;
import com.example.cache.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class HashService {
    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private RedisUtil redisUtil;


    public void setHash(Student student){
        HashOperations ofh = redisTemplate.opsForHash();
        String name = student.getName();
//        redisUtil.hset("dss","id",student.getId());
        ofh.put("dss","id",student.getId());
        ofh.put(name,"name",student.getName());
        ofh.put(name,"mobile",student.getMobile());
        ofh.put(name,"sex",student.getSex());
//        redisUtil.hset()
    }

    public Student getHash(String name){
        Student student = new Student();
        int id = (int) redisTemplate.opsForHash().get(name,"id");
        String name1 = (String) redisTemplate.opsForHash().get(name,"name");
        String mobile = (String) redisTemplate.opsForHash().get(name,"mobile");
        String sex = (String) redisTemplate.opsForHash().get(name,"sex");
        student.setSex(sex);
        student.setName(name1);
        student.setMobile(mobile);
        student.setId(id);
        return student;
    }
}
