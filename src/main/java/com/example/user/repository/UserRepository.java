package com.example.user.repository;

import java.util.Optional;

import com.example.user.entity.User;

public interface UserRepository {
	boolean existsByUsername(String username);
	User save(User user);
	Optional<User> findByUsername(String username);
}
