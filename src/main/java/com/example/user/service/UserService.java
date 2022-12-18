package com.example.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.user.dto.LoginForm;
import com.example.user.dto.RegisterForm;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.util.JWTGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
	private final UserRepository userRepository;
	private final JWTGenerator jwtGenerator;

	public void existUser(String username) {
		if (userRepository.existsByUsername(username)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already exist username",
				new IllegalArgumentException());
		}
	}

	public void resister(final RegisterForm request) {
		validateRegister(request.getUsername());
		User user = request.toEntity();
		userRepository.save(user);
	}

	public String login(final LoginForm request) {
		String username = validateLogin(request);
		return jwtGenerator.generateToken(username);
	}

	public User loadUserByUsername(final String username) {
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Please check username and password"));
	}

	private String validateLogin(LoginForm request) {
		User user = loadUserByUsername(request.getUsername());

		if (!user.match(request.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Please check username and password");
		}
		return user.getUsername();
	}

	private void validateRegister(String username) {
		existUser(username);
	}

}
