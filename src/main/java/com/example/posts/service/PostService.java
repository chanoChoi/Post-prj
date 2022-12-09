package com.example.posts.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.posts.dto.PostForm;
import com.example.posts.entity.Post;
import com.example.posts.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
	private final PostRepository postRepository;

	@Transactional(readOnly = true)
	public List<Post> fetchAll() {
		return postRepository.findAllByOrderByCreatedAtDesc();
	}

	public Post createPost(PostForm.Request request) {
		// dto로 변환하는 기능을 담당하는 class가 따로 필요할 수도 있다고 생각
		Post post = Post.convertPostFormRequestToPostEntity(request);
		return postRepository.save(post);
	}

	@Transactional(readOnly = true)
	public Post loadPostById(Long id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post with id{" + id + "} Not Found"));
	}

	public Post updatePost(PostForm.Request request) {
		Post post = loadPostById(request.getId());
		// validation을 담당하는 class가 따로 필요할 수도 있다고 생각
		checkValidation(request.getPassword(), post);
		post.updatePost(request);
		return postRepository.save(post);
	}


	public void removePost(Long id, String password) {
		Post post = loadPostById(id);
		checkValidation(password, post);
		postRepository.deleteById(id);
	}

	private void checkValidation(String password, Post post) {
		if (!checkPassword(password, post)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong Password");
		}
	}

	private boolean checkPassword(String password, Post post) {
		return post.match(password);
	}

}
