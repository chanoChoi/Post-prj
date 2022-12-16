package com.example.posts.dto;

import java.time.LocalDateTime;

import com.example.posts.entity.Post;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostForm {
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Request {
		@NotNull
		private Long id;
		@NotNull
		private String name;
		@NotNull
		private String password;
		@NotNull
		private String title;
		@NotNull
		private String content;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {
		@NotNull
		private String name;
		@NotNull
		private String title;
		@NotNull
		private String content;
		@NotNull
		private LocalDateTime createdAt;

		public static Response convertPostEntityToPostFormResponse(Post post) {
			return Response.builder()
				.name(post.getName())
				.title(post.getTitle())
				.content(post.getContent())
				.createdAt(post.getCreatedAt())
				.build();
		}
	}
}
