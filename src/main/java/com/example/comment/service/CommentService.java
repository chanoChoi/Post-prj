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
	public Comment updateComment(Long postId, String username, String content) {
		Post post = postService.loadPostById(postId);
		User user = userService.loadUserByUsername(username);
		Comment comment = findCommentByUserIdAndPostId(user.getId(), post.getId());
		comment.updateContent(content);
		return comment;
	}

	public void deleteComment(Long postId, String username) {
		Post post = postService.loadPostById(postId);
		User user = userService.loadUserByUsername(username);
		Comment comment = findCommentByUserIdAndPostId(user.getId(), post.getId());
		commentRepository.deleteById(comment.getId());
	}
}
