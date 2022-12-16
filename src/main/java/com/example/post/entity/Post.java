package com.example.post.entity;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.global.BaseEntity;
import com.example.post.dto.PostForm;
import com.example.user.entity.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "post_id"))
@Table(name = "Posts")
@Entity
public class Post extends BaseEntity {
	@Column(nullable = false, length = 45)
	private String title;
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;

	public static Post convertPostFormRequestToPostEntity(PostForm.Request request) {
		return Post.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.build();
	}

	public void updatePost(PostForm.Request request) {
		this.content = request.getContent();
		this.title = request.getTitle();
	}

	public String getName() {
		return this.user.getUsername();
	}

	public void addUser(User user) {
		this.user = user;
	}

	public boolean checkWriter(String username) {
		return !Objects.equals(this.getName(), username);
	}
}
