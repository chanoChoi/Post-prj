package com.example.user.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.global.BaseEntity;
import com.example.post.entity.Post;

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
	@Column(nullable = false, unique = true, length = 45)
	private String password;
	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Post> posts = new ArrayList<>();

	public boolean match(String password) {
		return Objects.equals(this.password, password);
	}

	public void removePost(Post post) {
		this.posts = this.posts.stream()
			.filter(p -> p != post)
			.collect(Collectors.toList());
	}

	public void addPost(Post post) {
		this.posts.add(post);
		post.addUser(this);
	}
}
