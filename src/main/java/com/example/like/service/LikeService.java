package com.example.like.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.comment.entity.Comment;
import com.example.comment.service.CommentService;
import com.example.like.entity.Like;
import com.example.like.repository.LikeRepository;
import com.example.post.entity.Post;
import com.example.post.service.PostService;
import com.example.user.entity.User;
import com.example.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {
	private final LikeRepository likeRepository;
	private final PostService postService;
	private final UserService userService;
	private final CommentService commentService;

	public static final String TYPE_POST = "POST";
	public static final String TYPE_COMMENT = "COMMENT";

	public String pushLikeAtPost(Long postId, String username) {
		User user = userService.loadUserByUsername(username);
		return toggleLike(user, TYPE_POST, postId);
	}
	public String pushLikeAtComment(Long commentId, String username) {
		User user = userService.loadUserByUsername(username);
		return toggleLike(user, TYPE_COMMENT, commentId);
	}

	private String toggleLike(User user, String type, Long referenceId) {
		// 좋아요 카운트 수 감소 로직 필요
		Optional<Like> likeOrNull = likeRepository.findByUserId(user.getId());

		if (likeOrNull.isPresent()) {
			Like like = likeOrNull.get();
			likeRepository.deleteById(like.getId());
			return "DELETE";
		}

		if (type.equals(TYPE_POST)) {
			Post post = postService.loadPostById(referenceId);
			post.pushLike();
			Like like = Like.builder()
				.post(post)
				.user(user)
				.build();
			likeRepository.save(like);
		} else {
			Comment comment = commentService.findCommentById(referenceId);
			comment.pushLike();
			Like like = Like.builder()
				.comment(comment)
				.user(user)
				.build();
			likeRepository.save(like);
		}
		return "PUSH";
	}
}
