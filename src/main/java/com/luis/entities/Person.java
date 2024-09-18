package com.luis.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
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
	@NotNull
	private String description;
	private String img;
	
	public Person(String name, String lastname, String description, String img) {
		this.name = name;
		this.lastname = lastname;
		this.description = description;
		this.img = img;
	}
}
