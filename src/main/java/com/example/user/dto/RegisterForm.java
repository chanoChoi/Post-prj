package com.example.user.dto;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.global.Validable;
import com.example.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterForm implements Validable {
	private String username;
	private String password;
	//!this.username.matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{4,10}&")
	// ||
	// 			!this.password.matches("[a-zA-Z0-9]")
	@Override
	public void validate() {
		if (!(4 <= this.username.length() && this.username.length() <= 10)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유저명은 4자 이상 10자 이하, 영문소문자와 숫자로만 구성되어야 합니다");
		}
		if (!(8 <= this.password.length() && this.password.length() <= 15)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상 15자 이하, 영문대소문자와 숫자로만 구성되어야 합니다");
		}
	}

	public User toEntity() {
		return User.builder()
			.username(this.username)
			.password(this.password)
			.build();
	}
}
