package com.example.cache.common.service;

import com.example.cache.common.dao.StudentMapper;
import com.example.cache.common.model.Student;
import com.example.cache.ehcache.util.EHCacheUtils;
import com.example.cache.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class StudentService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    @SuppressWarnings("all")
    private StudentMapper studentMapper;

    @Cacheable(value = "demoCache")
    public List<Student> getStudents(){
        return studentMapper.getStudents();
    }

//    @Cacheable(value = "demoCache")
    public void addStudent(Student student){
        Random random = new Random();

        String key = random.longs().toString();
        //存redis 
//        redisUtil.set(key,student);
        //存EHCache
//        EHCacheUtils.setCache("demoCache",key,student);
        //存数据库
        studentMapper.addStudent(student);
    }
}
