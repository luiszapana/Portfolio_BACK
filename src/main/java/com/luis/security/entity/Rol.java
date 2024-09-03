package com.luis.security.entity;

import com.luis.security.enums.RolName;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Rol {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@Enumerated(EnumType.STRING)
	private RolName rolName;
	
	public Rol(@NotNull RolName rolName) {
		this.rolName = rolName;
	}
	
	
}
