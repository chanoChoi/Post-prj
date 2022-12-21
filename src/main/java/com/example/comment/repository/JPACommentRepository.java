package com.example.comment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.comment.entity.Comment;

public interface JPACommentRepository extends JpaRepository<Comment, Long>, CommentRepository {
	@Query(value = "select c from Comment as c where c.user.id=:userId and c.post.id=:postId")
	Optional<Comment> findCommentByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
	@Query(value = "select count(c) from Comment as c where c.user.id=:userId and c.post.id=:postId")
	int existCommentByUserIdAndPostId(Long userId, Long postId);
}
