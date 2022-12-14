package com.example.post.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.comment.entity.Comment;
import com.example.comment.repository.CommentRepository;
import com.example.post.dto.PostForm;
import com.example.post.entity.Post;
import com.example.post.repository.PostRepository;
import com.example.user.entity.User;
import com.example.user.service.UserService;
import com.example.user.type.UserRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final UserService userService;

	@Transactional(readOnly = true)
	public Post loadPostById(Long id) {
		return postRepository.findById(id) // 구현체가 Optional 을 반환하게하면 사용할 수 있을듯?
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Post with id{" + id + "} Not Found"));
	}

	@Transactional(readOnly = true)
	public List<Post> fetchAll() {
		return postRepository.findAllByOrderByCreatedAtDesc();
	}

	public Post createPost(PostForm.Request request, String username) {
		User user = userService.loadUserByUsername(username);
		Post post = request.convertToPost();
		user.addPost(post);
		return postRepository.save(post);
	}

	public Post updatePost(PostForm.Request request, Long id, String username) {
		Post post = loadPostById(id);
		validateHasAuthorization(username, post);
		post.updatePost(request);
		return postRepository.save(post);
	}

	public void removePost(Long id, String username) {
		Post post = loadPostById(id);
		validateHasAuthorization(username, post);
		postRepository.deleteById(id);
	}

	private void validateHasAuthorization(String username, Post post) {
		User user = userService.loadUserByUsername(username);

		if (user.getRole() != UserRole.ADMIN) {
			if (!post.checkWriter(user.getUsername())){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "작성자만 삭제/수정할 수 있습니다.");
			}
		}
	}
}
