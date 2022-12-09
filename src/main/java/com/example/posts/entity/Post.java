package com.example.posts.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.posts.dto.PostForm;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "Posts")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 45)
	private String title;
	@Column(nullable = false, length = 45)
	private String name;
	@Column(nullable = false)
	private String content;
	@Column(nullable = false, length = 45)
	private String password;
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;

	public static Post convertPostFormRequestToPostEntity(PostForm.Request request) {
		return Post.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.name(request.getName())
			.password(request.getPassword())
			.build();
	}

	public void updatePost(PostForm.Request request) {
		this.name = request.getName();
		this.content = request.getContent();
		this.title = request.getTitle();
	}

	public boolean match(String password) {
		return Objects.equals(this.password, password);
	}
}
