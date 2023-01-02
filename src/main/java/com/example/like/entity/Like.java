package com.example.like.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.comment.entity.Comment;
import com.example.global.BaseEntity;
import com.example.post.entity.Post;
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
@AttributeOverride(name = "id", column = @Column(name = "like_id"))
@Table(name = "Likes")
@Entity
public class Like extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "post_id", referencedColumnName = "post_id")
	private Post post;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
	private Comment comment;
}
