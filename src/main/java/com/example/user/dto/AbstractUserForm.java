package com.example.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public class AbstractUserForm {
	protected String username;
	protected String password;
}
