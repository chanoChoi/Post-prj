package com.example.user.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.comment.entity.Comment;
import com.example.global.BaseEntity;
import com.example.like.entity.Like;
import com.example.post.entity.Post;
import com.example.user.type.UserRole;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Table(name = "Users")
@Entity
public class User extends BaseEntity {
	@Column(nullable = false, unique = true, length = 45)
	private String username;
	@Column(nullable = false, length = 45)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole role;
	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Post> posts = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Like> likes = new ArrayList<>();

	public boolean match(String password) {
		return Objects.equals(this.password, password);
	}

	public void addPost(Post post) {
		this.posts.add(post);
		post.addUser(this);
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
}
