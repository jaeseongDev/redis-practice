package com.example.loadtest.controller;

import com.example.loadtest.domain.Comment;
import com.example.loadtest.domain.CommentRepository;
import com.example.loadtest.domain.Post;
import com.example.loadtest.domain.PostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/api/comment/save")
    public String save() throws JsonProcessingException {
        // 1. DB에 저장 로직
        Comment temp = new Comment("댓글 내용", "댓글쓴이");
        Comment comment = commentRepository.save(new Comment("댓글 내용", "댓글쓴이"));

        // 2. Redis에 데이터 저장 or 갱신 (key, value)
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        String key = "comment" + comment.getId(); // key : 해당 값을 알아볼 수 있을만한 고유값
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(comment);
        stringValueOperations.set(key, value); // value : 데이터를 조회하고 싶은 형태
        return value;
    }

    @GetMapping("/api/comment")
    public String find(@RequestParam("id") Long commentId) throws JsonProcessingException {
        // 1. Redis에 해당 key값으로 찾았을 때, 데이터가 존재하는 지 확인
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        String key = "comment" + commentId;
        String value = stringValueOperations.get(key);
        Comment comment = null;
        // 2. 존재한다면 Redis로부터 값을 조회해와서 사용자한테 바로 return하고 끝!
        if (value != null) {
            ObjectMapper mapper = new ObjectMapper();
            comment = mapper.readValue(value, Comment.class);
        }

        // if) 존재하지 않는다면, DB로부터 조회해와서 Redis에 값을 저장해놓고, 사용자한테 return하고 끝!
        if (value == null) {
            Optional<Comment> possibleComment = commentRepository.findById(commentId);
            comment = possibleComment.get();
        }
        System.out.println(comment);
        return "ok";
    }
}
