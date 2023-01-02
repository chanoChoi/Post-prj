package com.example.comment.web;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.comment.entity.Comment;
import com.example.comment.service.CommentService;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/comments")
@RestController
public class CommentController {
	private final CommentService commentService;
	@PostMapping(value = "/{id}")
	public ResponseEntity<?> writeComment(@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable(name = "id") Long postId, @PathParam(value = "content") String content) {
		Assert.notNull(content, "content must be Not null");
		Comment comment = commentService.writeComment(postId, userDetails.getUsername(), content);
		return ResponseEntity.status(HttpStatus.CREATED).body(comment.getContent());
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateComment(@AuthenticationPrincipal UserDetails userDetails,
		@PathVariable(name = "id") Long commentId, @PathParam(value = "content") String content) {
		Assert.notNull(content, "content must be Not null");
		Comment comment = commentService.updateComment(commentId, userDetails.getUsername(), content);
		return ResponseEntity.ok(comment.getContent());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteComment(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "id") Long commentId) {
		commentService.deleteComment(commentId, userDetails.getUsername());
		return ResponseEntity.ok("Comment is gone...say good bye");
	}

}
