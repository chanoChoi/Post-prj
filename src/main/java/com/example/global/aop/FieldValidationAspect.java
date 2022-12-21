package com.example.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.example.global.DefaultValidate;
import com.example.user.dto.LoginForm;
import com.example.user.dto.RegisterForm;

import lombok.RequiredArgsConstructor;

@Aspect
@RequiredArgsConstructor
@Component
public class FieldValidationAspect {
	private final DefaultValidate validate;

	@Before("execution(* com.example.user.web.UserController.*(..))")
	public void validateFiled(JoinPoint joinPoint) {
		Object arg = joinPoint.getArgs()[0];
		if (arg instanceof RegisterForm) {
			RegisterForm arg1 = (RegisterForm) arg;
			validate.validate(arg1.getUsername(), arg1.getPassword());
		} else if (arg instanceof LoginForm) {
			LoginForm arg2 = (LoginForm)arg;
			validate.validate(arg2.getUsername(), arg2.getPassword());
		}

	}
}
