package com.example.cache.ehcache.controller;

import com.example.cache.ehcache.service.CacheAbleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cache")
public class CacheAbleController {
    @Autowired
    private CacheAbleService cacheAbleService;

    @GetMapping("stu")
    public Object getStudent(String name,String sex,int id,String mobile){
        return cacheAbleService.getStudentByName(name,sex,id,mobile);
    }

//    @GetMapping("stuByMobile")
//    public Object getByStudent(String name,String sex,int id,String mobile){
//        return cacheAbleService.getStudentByMobile(name,sex,id,mobile);
//    }
}
