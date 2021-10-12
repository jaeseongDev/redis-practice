package com.example.loadtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisSampleService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void getRedisStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        System.out.println("Redis Key: " + key);
        System.out.println("Redis value: " + stringValueOperations.get(key));
    }

    public void set(String key, String value) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        System.out.println("Set Redis Key: " + key);
        System.out.println("Set Redis value: " + value);
        stringValueOperations.set(key, value);
    }

    public String get(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        System.out.println("Get Redis Key: " + key);
        System.out.println("Get Redis value: " + stringValueOperations.get(key));
        return stringValueOperations.get(key);
    }
}
