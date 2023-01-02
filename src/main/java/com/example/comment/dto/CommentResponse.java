package com.example.comment.dto;

import java.time.LocalDateTime;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponse {
	@NotNull
	private Long id;
	@NotNull
	private String content;
	@NotNull
	private String username;
	@NotNull
	private int likeCnt;
	@NotNull
	private LocalDateTime createdAt;
}
