package com.example.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class JWTGeneratorTest {
	private JWTGenerator jwtGenerator;

	@BeforeEach
	public void setUp() {
		this.jwtGenerator = new JWTGenerator("secret");
	}

	@Test
	void success_generateToken() {
	//  given
	//  when
		String token = jwtGenerator.generateToken("username");
		//  then
		assertThat(token).as("Token is null").isNotNull();
	}

	@Test
	void throwException_validateToken() {
	//  given
	//  when
		assertThatThrownBy(() -> jwtGenerator.validateToken("token"))
			.isInstanceOf(ResponseStatusException.class)
			.hasMessageContaining("Token is expired or incorrect");
	//  then
	}
}