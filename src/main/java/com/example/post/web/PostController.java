package com.example.post.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping
	public ResponseEntity<PostForm.Response> createPost(@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody final PostForm.Request form) {
		Post post = postService.createPost(form, userDetails.getUsername());
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(post.convertToResponse());
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<PostForm.Response> updatePost(@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody final PostForm.Request form,
		@PathVariable final Long id) {
		Assert.notNull(id, ID_MUST_NOT_BE_NULL);
		Post post = postService.updatePost(form, id, userDetails.getUsername());
		return ResponseEntity.ok(post.convertToResponse());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> removePost(@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable final Long id) {
		Assert.notNull(id, ID_MUST_NOT_BE_NULL);
		postService.removePost(id, userDetails.getUsername());
		return ResponseEntity.ok("삭제 완료");
	}
}
