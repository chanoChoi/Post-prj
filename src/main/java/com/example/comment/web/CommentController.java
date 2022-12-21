package com.example.comment.web;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.comment.entity.Comment;
import com.example.comment.service.CommentService;
import com.example.global.aop.PreAuthorize;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/comments")
@RestController
public class CommentController {
	private final CommentService commentService;

	@PreAuthorize
	@PostMapping(value = "/{id}")
	public ResponseEntity<?> writeComment(HttpServletRequest request,
		@PathVariable(name = "id") Long postId, @PathParam(value = "content") String content) {
		Assert.notNull(content, "content must be Not null");
		String username = getUsernameFromRequest(request);
		Comment comment = commentService.writeComment(postId, username, content);
		return ResponseEntity.status(HttpStatus.CREATED).body(comment.getContent());
	}

	@PreAuthorize
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateComment(HttpServletRequest request,
		@PathVariable(name = "id") Long postId, @PathParam(value = "content") String content) {
		Assert.notNull(content, "content must be Not null");
		String username = getUsernameFromRequest(request);
		Comment comment = commentService.updateComment(postId, username, content);
		return ResponseEntity.ok(comment.getContent());
	}

	@PreAuthorize
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteComment(HttpServletRequest request, @PathVariable(name = "id") Long postId) {
		String username = getUsernameFromRequest(request);
		commentService.deleteComment(postId, username);
		return ResponseEntity.ok("Comment is gone...say good bye");
	}

	private static String getUsernameFromRequest(HttpServletRequest request) {
		return request.getAttribute("username").toString();
	}
}
