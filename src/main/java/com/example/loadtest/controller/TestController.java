package com.example.loadtest.controller;

import com.example.loadtest.domain.Comment;
import com.example.loadtest.domain.CommentRepository;
import com.example.loadtest.domain.Post;
import com.example.loadtest.domain.PostRepository;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final StringRedisTemplate stringRedisTemplate;

    public TestController(PostRepository postRepository, CommentRepository commentRepository, StringRedisTemplate stringRedisTemplate) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/set")
    public String set(@RequestParam("key") String key, @RequestParam("value") String value) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(key, value);
        return "ok";
    }

    @GetMapping("/get")
    public String get(@RequestParam("key") String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        String result = stringValueOperations.get(key);
        return result;
    }
}
