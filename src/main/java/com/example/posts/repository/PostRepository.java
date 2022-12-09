package com.example.posts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.posts.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByOrderByCreatedAtDesc();
}
