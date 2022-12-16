package com.example.post.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.post.dto.PostForm;
import com.example.post.entity.Post;
import com.example.post.repository.PostRepository;
import com.example.user.entity.User;
import com.example.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
	private final PostRepository postRepository;
	private final UserService userService;
	// private final JWTGenerator jwtGenerator;

	@Transactional(readOnly = true)
	public Post loadPostById(Long id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Post with id{" + id + "} Not Found", new IllegalArgumentException()));
	}

	@Transactional(readOnly = true)
	public List<Post> fetchAll() {
		return postRepository.findAllByOrderByCreatedAtDesc();
	}

	public Post createPost(PostForm.Request request, String username) {
		User user = userService.loadUserByUsername(username);
		Post post = Post.convertPostFormRequestToPostEntity(request);
		user.addPost(post);
		return postRepository.save(post);
	}

	public Post updatePost(PostForm.Request request, Long id, String username) {
		Post post = loadPostById(id);
		if (post.checkWriter(username)){
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한 없음");
		}
		post.updatePost(request);
		return postRepository.save(post);
	}

	public void removePost(Long id, String username) {
		Post post = loadPostById(id);
		if (post.checkWriter(username)){
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한 없음");
		}
		postRepository.deleteById(id);
	}



}
