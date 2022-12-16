package com.example.post.web;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.post.dto.PostForm;
import com.example.post.entity.Post;
import com.example.post.service.PostService;
import com.example.user.entity.User;
import com.example.util.JWTGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PostController.class)
class PostControllerTest {
	@MockBean
	private PostService postService;
	@MockBean
	private JWTGenerator generator;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	private User user1;
	private Post post1;
	private Post post2;

	@BeforeEach
	public void SetUp() {
		userSetup();
		postSetup();
	}
	private void userSetup() {
		user1 = User.builder()
			.username("username1")
			.password("password1")
			.posts(new ArrayList<>())
			.build();
	}
	private void postSetup() {
		post1 = Post.builder()
			.user(user1)
			.title("title1")
			.content("content1")
			.build();

		post2 = Post.builder()
			.user(user1)
			.title("title2")
			.content("content2")
			.build();
	}

	@Test
	void success_getPost() throws Exception {
	//  given
		given(postService.loadPostById(anyLong()))
			.willReturn(post1);
	//  when
		mockMvc.perform(get("/api/v1/posts/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(post1.getName()))
			.andExpect(jsonPath("$.title").value(post1.getTitle()))
			.andExpect(jsonPath("$.content").value(post1.getContent()));
	//  then
	}

	@Test
	void success_getPosts() throws Exception{
	//  given
		given(postService.fetchAll())
			.willReturn(new ArrayList<>(Arrays.asList(post1, post2)));
	//  when
		mockMvc.perform(get("/api/v1/posts"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].title").value(post1.getTitle()))
			.andExpect(jsonPath("$[1].title").value(post2.getTitle()));
	//  then
	}

	@Test
	void success_createPost() throws Exception{
	//  given
		String token = "Token";
		PostForm.Request request = PostForm.Request
			.builder()
			.title(post1.getTitle())
			.content(post1.getContent())
			.build();
		given(generator.getJWTFromToken(any(HttpServletRequest.class)))
			.willReturn(token);
		given(generator.validateToken(anyString()))
			.willReturn(true);
		given(generator.getUsernameFromToken(anyString()))
			.willReturn(user1.getUsername());
		given(postService.createPost(any(PostForm.Request.class), anyString()))
			.willReturn(post1);
	//  when
		mockMvc.perform(post("/api/v1/posts")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsBytes(request)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value(post1.getName()))
			.andExpect(jsonPath("$.title").value(post1.getTitle()))
			.andExpect(jsonPath("$.content").value(post1.getContent()));
			// .andExpect(jsonPath("$.createdAt").value(post1.getCreatedAt()));
	//  then
	}
}