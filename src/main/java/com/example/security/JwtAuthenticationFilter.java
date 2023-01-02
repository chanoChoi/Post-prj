package com.example.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.util.JWTGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final UserDetailsService userDetailsService;
	private final JWTGenerator jwtGenerator;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = jwtGenerator.getJWTFromToken(request);

		if (token != null) {
			jwtGenerator.validateToken(token);
			UserDetails user = userDetailsService.loadUserByUsername(
				jwtGenerator.getUsernameFromToken(token));
			Authentication authentication = new UsernamePasswordAuthenticationToken(user,
				null, user.getAuthorities());
			System.out.println(user.getAuthorities().size());
			for (GrantedAuthority a : user.getAuthorities()) {
				System.out.println(a.getAuthority());
			}
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}
}
