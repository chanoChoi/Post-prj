package com.example.like.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.like.service.LikeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/likes")
@RestController
public class LikeController {
	private final LikeService likeService;

	@PostMapping(path = "/posts/{postId}")
	public ResponseEntity<?> pushLikeAtPost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long postId) {
		String result = likeService.pushLikeAtPost(postId, userDetails.getUsername());
		String body = result.equals("DELETE") ? "좋아요 취소" : "즇아요 꾹";
		return ResponseEntity.ok(body);
	}

	@PostMapping(path = "/comments/{commentId}")
	public ResponseEntity<?> pushLikeAtComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long commentId) {
		String result = likeService.pushLikeAtComment(commentId, userDetails.getUsername());
		String body = result.equals("DELETE") ? "좋아요 취소" : "즇아요 꾹";
		return ResponseEntity.ok(body);
	}
}
