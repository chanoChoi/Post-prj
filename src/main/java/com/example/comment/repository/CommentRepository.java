package com.example.comment.repository;

import java.util.Optional;

import com.example.comment.entity.Comment;

public interface CommentRepository {
	Comment save(Comment comment);
	Optional<Comment> findCommentByUserIdAndPostId(Long userId, Long postId);
	int existCommentByUserIdAndPostId(Long userId, Long postId);
	void deleteById(Long id);
	Optional<Comment> findById(Long commentId);
}
