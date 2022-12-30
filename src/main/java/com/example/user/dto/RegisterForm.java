package com.example.user.dto;

import com.example.user.entity.User;
import com.example.user.type.UserRole;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class RegisterForm extends AbstractUserForm{
	public User toEntity() {
		return User.builder()
			.username(this.username)
			.password(this.password)
			.role(UserRole.USER)
			.build();
	}
}
