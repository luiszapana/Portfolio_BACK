package com.luis.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDto {
	private String token;
	private String bearer = "Bearer";
	private String Username;
	private Collection<? extends GrantedAuthority> authorities;
	
	public JwtDto(String token, String username, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.token = token;
		Username = username;
		this.authorities = authorities;
	}
}
