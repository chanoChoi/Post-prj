package com.example.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import lombok.Getter;
import lombok.Setter;

@Getter
@Component
public class JWTGenerator {
	private final String secret;
	private final int expire = 60 * 60 * 24 * 1000;

	public JWTGenerator(@Value("$(spring.jwt.secret)") String secret) {
		this.secret = secret;
	}

	public String generateToken(String username) {
		Date curDate = new Date();
		Date expireDate = new Date(curDate.getTime() + expire);

		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(curDate)
			.setExpiration(expireDate)
			.signWith(SignatureAlgorithm.HS512, secret)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
		}
	}

	public String getUsernameFromToken(String token) {
		Assert.notNull(token, "Token is Empty");
		Claims claims = Jwts.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token)
			.getBody();
		return claims.getSubject();
	}

	public String getJWTFromToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
