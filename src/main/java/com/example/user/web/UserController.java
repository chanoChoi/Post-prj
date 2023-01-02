package com.example.user.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.dto.LoginForm;
import com.example.user.dto.RegisterForm;
import com.example.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // private final 로 선언된 객체들을 자동 DI 해줍니다. (주입되는 객체는 IoC 컨테이너에 Bean 으로 등록이 되어 있어야합니다)
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
	private final UserService userService;

	@PostMapping(value = "/register")
	public ResponseEntity<?> register(@RequestBody RegisterForm request) {
		userService.resister(request);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
	}

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginForm request) {
		String token = userService.login(request);
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		return ResponseEntity.ok().headers(headers).body("로그인 완료");
	}
}
