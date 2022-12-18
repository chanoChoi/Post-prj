package com.example.user.web;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.user.dto.LoginForm;
import com.example.user.dto.RegisterForm;
import com.example.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

// @SpringJUnitConfig(AspectConfiguration.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
	@MockBean
	private UserService userService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void success_register() throws Exception {
		//  given
		RegisterForm registerForm = RegisterForm.builder()
			.username("test")
			.password("test")
			.build();

		//  when
		mockMvc.perform(post("/api/v1/users/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(registerForm)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(content().string("회원가입 성공"));
	}

	@Test
	void success_login() throws Exception {
	//  given
		String token = "Token";
		LoginForm loginForm = LoginForm.builder()
			.username("username")
			.password("password")
			.build();

		given(userService.login(any(LoginForm.class)))
			.willReturn(token);
	//  when
		mockMvc.perform(post("/api/v1/users/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(loginForm)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("로그인 완료"))
			.andExpect(header().string("Authorization", token));
	//  then
	}
}