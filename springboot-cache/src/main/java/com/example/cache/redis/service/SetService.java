package com.example.cache.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SetService {
    @Autowired
    private RedisTemplate redisTemplate;



}
