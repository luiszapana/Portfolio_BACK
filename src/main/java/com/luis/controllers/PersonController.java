package com.luis.controllers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luis.dto.PersonDto;
import com.luis.entities.Person;
import com.luis.security.controller.Message;
import com.luis.services.PersonService;

@RestController
@RequestMapping("/person")
@CrossOrigin (origins = "http://localhost:4200")
public class PersonController {
	@Autowired
	private PersonService service;
	
	@GetMapping("/list")
	public ResponseEntity<List<Person>> list(){
		List<Person> persons = service.list();
		return new ResponseEntity<>(persons, HttpStatus.OK);
	}
	
	@GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        if(!service.existsById(id))
            return new ResponseEntity<>(new Message("no existe"), HttpStatus.NOT_FOUND);
        Person person = service.getOne(id).get();
        return new ResponseEntity<>(person, HttpStatus.OK);
    }
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody PersonDto dto){
		if (StringUtils.isBlank(dto.getName())) {
			return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		}
		if (service.existsByName(dto.getName())) {
			return new ResponseEntity<>(new Message("Persona existente"), HttpStatus.BAD_REQUEST);
		}
		Person person = new Person(dto.getName(), dto.getLastname(), dto.getDescription(), dto.getImg());
		service.save(person);
		return new ResponseEntity<>(new Message("Persona agregada"), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update (@PathVariable("id") Long id, @RequestBody PersonDto dto){
		if (!service.existsById(id)) {// si no existe
			return new ResponseEntity<>(new Message("El id no existe"), HttpStatus.BAD_REQUEST);
		}
		if (service.existsByName(dto.getName()) && service.getByName(dto.getName()).get().getId() != id) {
			return new ResponseEntity<>(new Message("Persona existente"), HttpStatus.BAD_REQUEST);
		}
		if (StringUtils.isBlank(dto.getName())) {
			return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		}
		Person person = service.getOne(id).get();
		person.setName(dto.getName());
		person.setLastname(dto.getLastname());
		person.setDescription(dto.getDescription());
		person.setImg(dto.getImg());
		service.save(person);
		return new ResponseEntity<>(new Message("Persona actualizada"), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		if (!service.existsById(id)) {// si no existe
			return new ResponseEntity<>(new Message("El id no existe"), HttpStatus.BAD_REQUEST);
		}
		service.delete(id);
	    return new ResponseEntity<>(new Message("Persona eliminada"), HttpStatus.OK);
	}	
}
