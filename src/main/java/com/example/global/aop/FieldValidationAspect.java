package com.example.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.example.global.Validator;
import com.example.user.dto.AbstractUserForm;

import lombok.RequiredArgsConstructor;

@Aspect
@RequiredArgsConstructor
@Component
public class FieldValidationAspect {
	private final Validator validate;

	@Before("execution(* com.example.user.web.UserController.*(..))")
	public void validateFiled(JoinPoint joinPoint) {
		AbstractUserForm arg = (AbstractUserForm)joinPoint.getArgs()[0];
		Validator.validate(arg.getUsername(), arg.getPassword());
	}
}
