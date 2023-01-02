package com.example.comment.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.comment.entity.Comment;
import com.example.comment.repository.CommentRepository;
import com.example.post.entity.Post;
import com.example.post.service.PostService;
import com.example.user.entity.User;
import com.example.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostService postService;
	private final UserService userService;

	public Comment findCommentById(Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 코멘트를 찾을 수 없습니다."));
	}

	private Comment findCommentByUserIdAndPostId(Long userId, Long postId) {
		return commentRepository.findCommentByUserIdAndPostId(userId, postId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"작성자만 삭제/수정할 수 있습니다."));
	}

	public Comment writeComment(Long postId, String username, String content) {
		Post post = postService.loadPostById(postId);
		User user = userService.loadUserByUsername(username);
		if (commentRepository.existCommentByUserIdAndPostId(user.getId(), post.getId()) > 0){
			throw new RuntimeException("정상접근 X");
		};
		Comment comment = Comment.builder()
			.content(content)
			.post(post)
			.user(user)
			.build();
		comment.addComment();
		return commentRepository.save(comment);
	}
	public Comment updateComment(Long commentId, String username,  String content) {
		Comment comment = findCommentById(commentId);
		User user = userService.loadUserByUsername(username);
		if (comment.validateAuthorize(user)) {
			throw new RuntimeException("NOT CORRECT ACCESS");
		};
		comment.updateContent(content);
		return comment;
	}

	public void deleteComment(Long commentId, String username) {
		Comment comment = findCommentById(commentId);
		User user = userService.loadUserByUsername(username);
		if (comment.validateAuthorize(user)) {
			throw new RuntimeException("NOT CORRECT ACCESS");
		};
		commentRepository.deleteById(comment.getId());
	}
}
