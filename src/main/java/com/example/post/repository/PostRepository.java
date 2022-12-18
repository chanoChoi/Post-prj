package com.example.post.repository;

import java.util.List;
import java.util.Optional;

import com.example.post.entity.Post;

public interface PostRepository {
	List<Post> findAllByOrderByCreatedAtDesc();
	Optional<Post> findById(Long id);
	Post save(Post post);
	void deleteById(Long id);
}
