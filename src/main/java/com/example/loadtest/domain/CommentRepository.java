package com.example.loadtest.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long>  {
    @Query(
        value = "SELECT *\n"
            + "FROM loadtest.comment as c\n"
            + "WHERE c.content LIKE '%글%'\n"
            + "ORDER BY c.writer DESC\n"
            + "LIMIT 496325, 100;",
        nativeQuery = true
    )
    List<Comment> findCommentsForLoadTest();
}
