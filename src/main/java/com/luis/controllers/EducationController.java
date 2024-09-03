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

import com.luis.dto.EducationDto;
import com.luis.entities.Education;
import com.luis.security.controller.Message;
import com.luis.services.EducationService;


@RestController
@RequestMapping("education")
@CrossOrigin (origins = "http://localhost:4200")
public class EducationController {
	@Autowired
	private EducationService service;
	
	@GetMapping("/list")
	public ResponseEntity<List<Education>> list(){
		List<Education> educations = service.list();
		return new ResponseEntity<>(educations, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody EducationDto dto){
		if (StringUtils.isBlank(dto.getName())) {
			return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		}
		if (service.existsByName(dto.getName())) {
			return new ResponseEntity<>(new Message("Educación existente"), HttpStatus.BAD_REQUEST);
		}
		Education education = new Education(dto.getName(), dto.getDescription());
		service.save(education);
		return new ResponseEntity<>(new Message("Educación agregada"), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update (@PathVariable("id") int id, @RequestBody EducationDto dto){
		if (!service.existsById(id)) {// si no existe
			return new ResponseEntity<>(new Message("El id no existe"), HttpStatus.BAD_REQUEST);
		}
		if (service.existsByName(dto.getName()) && service.getByName(dto.getName()).get().getId() != id) {
			return new ResponseEntity<>(new Message("Educación existente"), HttpStatus.BAD_REQUEST);
		}
		if (StringUtils.isBlank(dto.getName())) {
			return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		}
		Education education = service.getOne(id).get();
		education.setName(dto.getName());
		education.setDescription(dto.getDescription());
		service.save(education);
		return new ResponseEntity<>(new Message("Educación actualizada"), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		if (!service.existsById(id)) {// si no existe
			return new ResponseEntity<>(new Message("El id no existe"), HttpStatus.BAD_REQUEST);
		}
		service.delete(id);
	    return new ResponseEntity<>(new Message("Educación eliminada"), HttpStatus.OK);
	}
	
	@GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        if(!service.existsById(id))
            return new ResponseEntity<>(new Message("no existe"), HttpStatus.NOT_FOUND);
        Education education = service.getOne(id).get();
        return new ResponseEntity<>(education, HttpStatus.OK);
    }
}
