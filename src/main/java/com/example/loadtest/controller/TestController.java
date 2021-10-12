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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final Object left = new Object();
    private final Object right = new Object();

    public TestController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/db-io")
    public ResponseEntity<Void> dbIo() {
        List<Comment> results = commentRepository.findCommentsForLoadTest();
        System.out.println(results.size());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/was-cpu")
    public ResponseEntity<Void> cpu() {
        String a = "a";
        for (int i = 0; i < 10000; i++) {
            a += i;
            if (i % 1000 == 0) {
                System.out.println(i);
            }
        }
        System.out.println("완료");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/infinite-loop")
    public ResponseEntity<Void> infinite() {
        boolean check = true;
        int i = 0;
        while(check) {
            i += 1;
            i = 0;
        }
        System.out.println(i);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/was-io")
    public ResponseEntity<Void> wasIo() throws IOException {
        Path path = Paths.get("./src/main/resources/video.mp4");
        byte[] bytes = Files.readAllBytes(path);

        Path newPath = Paths.get("./src/main/resources/newVideo.mp4");
        Files.write(newPath, bytes);
        System.out.println("완료");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/was-network")
    public ResponseEntity<byte[]> wasNetwork() throws IOException {
        Path path = Paths.get("./src/main/resources/video.mp4");
        byte[] bytes = Files.readAllBytes(path);
        System.out.println("완료");
        return ResponseEntity.ok(bytes);
    }

    @GetMapping("/left")
    public String leftRight() throws InterruptedException {
        synchronized (left) {
            Thread.sleep(5000);
            synchronized (right) {
                System.out.println("left - right");
            }
        }
        return "ok";
    }

    @GetMapping("/right")
    public String rightLeft() throws InterruptedException {
        synchronized (right) {
            Thread.sleep(5000);
            synchronized (left) {
                System.out.println("right - left");
            }
        }
        return "ok";
    }

//    @GetMapping("/get")
//    public String get() {
//        Optional<Post> byId = postRepository.findById(1L);
//        return byId.toString();
//    }
}
