package com.example.global.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.example.util.JWTGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Aspect
@Component
public class AspectPrac {
	private final JWTGenerator jwtGenerator;
	@Pointcut("@annotation(com.example.global.aop.PreAuthorize)") // 진입 장
	public void preauthorize(){};

	@Before("preauthorize()")
	public void validateToken(JoinPoint jp){
		// MethodSignature signature = (MethodSignature) jp.getSignature();
		// Method method = signature.getMethod();
		// PreAuthorize annotation = method.getAnnotation(PreAuthorize.class);

		HttpServletRequest request = (HttpServletRequest) jp.getArgs()[0];
		String token = jwtGenerator.getJWTFromToken(request);
		jwtGenerator.validateToken(token);
		String username = jwtGenerator.getUsernameFromToken(token);
		request.setAttribute("username", username);
	}
}

