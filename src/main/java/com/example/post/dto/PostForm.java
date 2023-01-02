package com.example.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.comment.dto.CommentResponse;
import com.example.post.entity.Post;
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
		private String title;
		@NotNull
		private String content;

		public Post convertToPost() {
			return Post.builder()
				.title(this.getTitle())
				.content(this.getContent())
				.build();
		}
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {
		@NotNull
		private Long id;
		@NotNull
		private String username;
		@NotNull
		private String title;
		@NotNull
		private String content;
		@NotNull
		private int likeCnt;
		@NotNull
		private LocalDateTime createdAt;
		@NotNull
		private List<CommentResponse> comments;
	}
}
