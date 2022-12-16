package com.example.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

	public void resister(final String username, final String password) {
		validateRegister(username);
		User user = User.createUser(username, password);
		userRepository.save(user);
	}

	public String login(String username, String password) {
		User user = loadUserByUsername(username);
		validateLogin(user, password);

		return jwtGenerator.generateToken(username);
	}

	public User loadUserByUsername(final String username) {
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Please check username and password"));
	}

	private void validateLogin(User user, String password) {
		if (!user.match(password)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Please check username and password");
		}
	}

	private void validateRegister(String username) {
		if (userRepository.existsByUsername(username)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already exist username",
				new IllegalArgumentException());
		}
	}

}
