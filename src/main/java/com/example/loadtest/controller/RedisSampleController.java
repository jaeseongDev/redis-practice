package com.example.loadtest.controller;

import com.example.loadtest.service.RedisSampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisSampleController {
    @Autowired
    private RedisSampleService redisSampleService;

    @PostMapping(value="/getRedisStringValue")
    public void getRedisStringValue(String key) {
        redisSampleService.getRedisStringValue(key);
    }

    @GetMapping("/set")
    public String set(@RequestParam("key") String key, @RequestParam("value") String value) {
        redisSampleService.set(key, value);
        return "set ok";
    }

    @GetMapping("/get")
    public String get(@RequestParam("key") String key) {
        return redisSampleService.get(key);
    }
}
