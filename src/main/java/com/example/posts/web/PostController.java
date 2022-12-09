package com.example.posts.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.posts.dto.PostForm;
import com.example.posts.entity.Post;
import com.example.posts.service.PostService;
import com.sun.istack.NotNull;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value = "/api/posts")
@RestController
public class PostController {
	private final PostService postService;

	@GetMapping
	public List<PostForm.Response> getPosts() {
		List<Post> posts = postService.fetchAll();
		return posts.stream()
			.map(PostForm.Response::convertPostEntityToPostFormResponse)
			.collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public PostForm.Response createPost(@NotNull @RequestBody final PostForm.Request request) {
		Post post = postService.createPost(request);
		return PostForm.Response
			.convertPostEntityToPostFormResponse(post);
	}

	@PutMapping(value = "/{id}")
	public PostForm.Response updatePost(@NotNull @RequestBody final PostForm.Request request) {
		Post post = postService.updatePost(request);
		return PostForm.Response
			.convertPostEntityToPostFormResponse(post);
	}

	@GetMapping(value = "/{id}")
	public PostForm.Response getPost(@NotNull @PathVariable final Long id) {
		return PostForm.Response
			.convertPostEntityToPostFormResponse(postService.loadPostById(id));
	}

	@DeleteMapping(value = "/{id}")
	public String removePost(@NotNull @PathVariable final Long id,@NotNull @RequestBody final String password) {
		postService.removePost(id, password);
		return "삭제 완료";
	}

}
