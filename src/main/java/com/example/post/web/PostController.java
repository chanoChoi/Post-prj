package com.example.post.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.global.aop.PreAuthorize;
import com.example.post.dto.PostForm;
import com.example.post.entity.Post;
import com.example.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/posts")
@RestController
public class PostController {
	private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";
	private final PostService postService;
	@GetMapping(value = "/{id}")
	public ResponseEntity<PostForm.Response> getPost(@PathVariable final Long id) {
		Assert.notNull(id, ID_MUST_NOT_BE_NULL);
		Post post = postService.loadPostById(id);
		return ResponseEntity.ok(post.convertToResponse());
	}

	@GetMapping
	public ResponseEntity<List<PostForm.Response>> getPosts() {
		List<Post> posts = postService.fetchAll();
		return ResponseEntity.ok(posts.stream()
			.map(Post::convertToResponse)
			.collect(Collectors.toList()));
	}

	@PreAuthorize
	@PostMapping
	public ResponseEntity<PostForm.Response> createPost(HttpServletRequest request,
		@RequestBody final PostForm.Request form) {
		String username = getUsernameFromRequest(request);
		Post post = postService.createPost(form, username);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(post.convertToResponse());
	}

	@PreAuthorize
	@PutMapping(value = "/{id}")
	public ResponseEntity<PostForm.Response> updatePost(HttpServletRequest request,
		@RequestBody final PostForm.Request form,
		@PathVariable final Long id) {
		Assert.notNull(id, ID_MUST_NOT_BE_NULL);
		String username = getUsernameFromRequest(request);
		Post post = postService.updatePost(form, id, username);
		return ResponseEntity.ok(post.convertToResponse());
	}

	@PreAuthorize
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> removePost(HttpServletRequest request,
		@PathVariable final Long id) {
		Assert.notNull(id, ID_MUST_NOT_BE_NULL);
		String username = getUsernameFromRequest(request);
		postService.removePost(id, username);
		return ResponseEntity.ok("삭제 완료");
	}

	private String getUsernameFromRequest(HttpServletRequest request) {
		return request.getAttribute("username").toString();
	}
}
