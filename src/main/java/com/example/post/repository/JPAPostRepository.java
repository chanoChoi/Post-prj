package com.example.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.post.entity.Post;

public interface JPAPostRepository extends JpaRepository<Post, Long>, PostRepository{
}
