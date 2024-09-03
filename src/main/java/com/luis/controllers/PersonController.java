package com.luis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luis.entities.Person;
import com.luis.interfaces.IPersonService;

@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class PersonController {
	@Autowired
	private IPersonService personService;
	
	@GetMapping("persons/list")
	public List<Person> getPerson(){
		return personService.getPersons();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("persons/create")
	public ResponseEntity<String> createPerson(@RequestBody Person person) {
	    personService.savePerson(person);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Persona creada con éxito");
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("persons/delete/{id}")
	public ResponseEntity<String> deletePerson(@PathVariable Long id) {
	    Person person = personService.findPerson(id);
	    if (person == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
	    }
	    personService.deletePerson(id);
	    return ResponseEntity.status(HttpStatus.OK).body("Persona eliminada con éxito");
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("persons/edit/{id}")
	public Person editPerson(@PathVariable Long id,
			@RequestParam("name") String name,
			@RequestParam("lastName") String lastName,
			@RequestParam("img") String img){
		Person person = personService.findPerson(id);
		person.setName(name);
		person.setLastname(lastName);
		person.setImg(img);
		personService.savePerson(person);
		return person;
	}
	
	@GetMapping("persons/list/profile")
	public Person findPerson(){
		return personService.findPerson((long)1);
	}
}
