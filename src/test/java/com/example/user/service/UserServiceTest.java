package com.example.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.user.dto.LoginForm;
import com.example.user.dto.RegisterForm;
import com.example.user.entity.User;
import com.example.user.repository.JPAUserRepository;
import com.example.util.JWTGenerator;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private JPAUserRepository JPAUserRepository;
	@Mock
	private JWTGenerator jwtGenerator;
	@InjectMocks
	private UserService userService;

	@Test
	void success_register() {
	//  given
		RegisterForm request = RegisterForm.builder()
			.username("username")
			.password("password")
			.build();
		given(JPAUserRepository.existsByUsername(anyString()))
			.willReturn(false);
	//  when
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		userService.resister(request);
		//  then
		verify(JPAUserRepository).save(captor.capture());
	}

	@Test
	void success_login() {
	//  given
		LoginForm request = LoginForm.builder()
			.username("username")
			.password("password")
			.build();
		String token = "token";
		User user = User.builder()
			.username("username")
			.password("password")
			.posts(new ArrayList<>())
			.build();
		given(JPAUserRepository.findByUsername(anyString()))
			.willReturn(Optional.of(user));
		given(jwtGenerator.generateToken(anyString()))
			.willReturn(token);
	//  when
		String returnedToken = userService.login(request);
		//  then
		assertThat(token).isEqualTo(returnedToken);
	}
}