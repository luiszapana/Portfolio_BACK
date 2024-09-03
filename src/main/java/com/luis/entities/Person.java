package com.luis.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min = 1, max = 50, message = "no cumple con la longitud")
	private String name;
	@NotNull
	@Size(min = 1, max = 50, message = "no cumple con la longitud")
	private String lastname;
	@Size(min = 1, max = 50, message = "no cumple con la longitud")
	private String img;
}
