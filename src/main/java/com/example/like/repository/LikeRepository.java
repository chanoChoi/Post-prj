package com.example.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.like.entity.Like;
import com.example.user.entity.User;

public interface LikeRepository extends JpaRepository<Like, Long> {
	@Query(value = "select l from Like as l where l.user.id=:userId")
	Optional<Like> findByUserId(@Param(value = "userId") Long userId);
}
