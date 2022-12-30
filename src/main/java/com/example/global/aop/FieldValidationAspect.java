package com.example.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.example.global.DefaultValidate;
import com.example.user.dto.AbstractUserForm;
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
		AbstractUserForm arg = (AbstractUserForm)joinPoint.getArgs()[0];
		validate.validate(arg.getUsername(), arg.getPassword());

	}
}
