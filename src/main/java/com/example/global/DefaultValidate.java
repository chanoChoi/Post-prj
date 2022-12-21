package com.example.global;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class DefaultValidate {
	public void validate(String username, String password) {
		System.out.println("실행");
		if (!username.matches("^[a-z0-9]{4,10}$")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유저명은 4자 이상 10자 이하, 영문소문자와 숫자로만 구성되어야 합니다");
		}
		if (!password.matches("^[a-zA-Z0-9]{8,15}$")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상 15자 이하, 영문대소문자와 숫자로만 구성되어야 합니다");
		}
	}
}
