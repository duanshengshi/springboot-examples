package com.example.cache.ehcache.controller;


import com.example.cache.common.model.Student;
import com.example.cache.ehcache.service.CacheAbleService;
import com.example.cache.ehcache.util.EHCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ehcache")
public class EHCacheController {

    @Autowired
    private CacheAbleService cacheAbleService;

    @GetMapping("getCacheNames")
    public Object getCacheNames(){
        return EHCacheUtils.getCacheManager().getCacheNames();
    }

    @GetMapping("setStuToEhCache")
    public Object setStuToEhCache(String name,String cacheName){
        Student student = new Student();
        student.setName(name);
        student.setSex("man");
        student.setMobile("123456");
        student.setId(1);
        EHCacheUtils.setCache(cacheName,name,student);
        return student;
    }

    @GetMapping("getStuFromEhCache")
    public Object getStuFromEhCache(String name,String cacheName){
//        List<Student> list =
        Student student = (Student)EHCacheUtils.getCache(cacheName,name);
        return student;
    }

    @GetMapping("getStu")
    public Object getStu(String name){
        return cacheAbleService.getStudentFromEHCache(name);
    }
}
