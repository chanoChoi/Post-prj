package com.example.user.dto;

import com.example.user.entity.User;
import com.example.user.type.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RegisterForm {
	private String username;
	private String password;

	public User toEntity() {
		return User.builder()
			.username(this.username)
			.password(this.password)
			.role(UserRole.USER)
			.build();
	}
}
