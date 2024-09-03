package com.luis.security.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

@Entity
@Data @NoArgsConstructor 
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	private String name;
	@NotNull
	@Column(unique = true)
	private String username;
	@NotNull
	private String email;
	@NotNull
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable (name = "user_rol", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles = new HashSet<>();
	
	public User(@NotNull String name, @NotNull String username, @NotNull String email, @NotNull String password) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
