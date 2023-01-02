package com.example.user.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private final String authority;

	public String getAuthority() {
		return this.authority;
	}
}
