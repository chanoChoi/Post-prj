package com.example.comment.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.comment.dto.CommentResponse;
import com.example.global.BaseEntity;
import com.example.post.entity.Post;
import com.example.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "comment_id"))
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity {
	@Column(nullable = false)
	private String content;
	// cascade maybe
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "post_id", referencedColumnName = "post_id")
	private Post post;

	public CommentResponse toCommentResponse() {
		return CommentResponse.builder()
			.username(user.getUsername())
			.content(content)
			.createdAt(this.getCreatedAt())
			.build();
	}

	public void addComment() {
		this.user.addComment(this);
		this.post.addComment(this);
	}

	public void updateContent(String content) {
		this.content = content;
	}
}
