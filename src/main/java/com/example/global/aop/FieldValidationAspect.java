package com.example.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.example.global.Validable;

@Aspect
@Component
public class FieldValidationAspect {
	@Before("execution(* com.example.user.web.UserController.*(..))")
	public void validateFiled(JoinPoint joinPoint) {
		Validable arg = (Validable)joinPoint.getArgs()[0];
		arg.validate();
	}
}
