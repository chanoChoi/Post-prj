package com.example.post.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.comment.entity.Comment;
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
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;

	@Builder.Default
	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Comment> comments = new ArrayList<>();

	public void updatePost(PostForm.Request request) {
		this.content = request.getContent();
		this.title = request.getTitle();
	}

	public PostForm.Response convertToResponse() {
		return PostForm.Response.builder()
			.username(this.getName())
			.title(this.title)
			.content(this.content)
			.createdAt(this.getCreatedAt())
			// 애플리케이션에서 댓글을 시간순 정렬 or DB에서 포스트를 가져올때 코멘트를 시간순 정렬하는 쿼리를 사용할 것인가
			.comments(this.comments.stream().map(Comment::toCommentResponse).collect(Collectors.toList()))
			.build();
	}

	public String getName() {
		return this.user.getUsername();
	}

	public void addUser(User user) {
		this.user = user;
	}

	public boolean checkWriter(String username) {
		return Objects.equals(this.getName(), username);
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
	}

	public Comment getComment(Comment comment) {
		return this.comments.stream()
			.filter(c -> Objects.equals(c.getId(), comment.getId()))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("TTTT"));
	}

	public void updateComment(Comment comment) {
		Comment comment1 = getComment(comment);
		comment1.updateContent(content);
	}
}
