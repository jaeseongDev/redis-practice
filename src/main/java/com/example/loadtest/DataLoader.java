package com.example.loadtest;

import com.example.loadtest.domain.Comment;
import com.example.loadtest.domain.CommentRepository;
import com.example.loadtest.domain.Post;
import com.example.loadtest.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public void run(String... args) {
        logger.info("확인");
//        for (int i = 0; i < 1_000; i++) {
//            StringBuilder sb = new StringBuilder();
//            for (int j = 0; j < 1_000_000; j++) {
//                sb.append("내용");
//            }
//            Post newPost = new Post(sb.toString(), "글쓴이" + i, "카테코리" + i);
//            Post savedPost = postRepository.save(newPost); //flush
//
//            Comment comment = new Comment("댓글", "글쓴이" + i);
//            savedPost.addComment(comment);
//            commentRepository.save(comment);
//            if (i % 10 == 0) {
//                System.out.println(i);
//            }
//        }
    }
}
