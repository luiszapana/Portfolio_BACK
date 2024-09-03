package com.luis.security.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class MainUser implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String username;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public static MainUser build (User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
											.map(rol -> new SimpleGrantedAuthority(rol.getRolName().name()))
											.collect(Collectors.toList());
		return new MainUser(user.getName(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
	    return true; // Puedes personalizar esta lógica según las necesidades de tu aplicación
	}

	@Override
	public boolean isAccountNonLocked() {
	    return true; // Puedes personalizar esta lógica según las necesidades de tu aplicación
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true; // Puedes personalizar esta lógica según las necesidades de tu aplicación
	}

	@Override
	public boolean isEnabled() {
	    return true; // Puedes personalizar esta lógica según las necesidades de tu aplicación
	}

}
