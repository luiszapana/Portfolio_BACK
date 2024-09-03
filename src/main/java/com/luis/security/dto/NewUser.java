package com.luis.security.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUser {
	private String name;
	private String username;
	private String email;
	private String password;
	private Set<String> roles = new HashSet<>();
}
