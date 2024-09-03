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

import com.luis.dto.ExperienceDto;
import com.luis.entities.Experience;
import com.luis.security.controller.Message;
import com.luis.services.ExperienceService;

@RestController
@RequestMapping("experience")
@CrossOrigin (origins = "http://localhost:4200")
public class ExperienceController {
	@Autowired
	private ExperienceService service;
	
	@GetMapping("/list")
	public ResponseEntity<List<Experience>> list(){
		List<Experience> experiences = service.list();
		return new ResponseEntity<>(experiences, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody ExperienceDto dto){
		if (StringUtils.isBlank(dto.getName())) {
			return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		}
		if (service.existsByName(dto.getName())) {
			return new ResponseEntity<>(new Message("Esa experiencia ya existe"), HttpStatus.BAD_REQUEST);
		}
		Experience experience = new Experience(dto.getName(), dto.getDescription());
		service.save(experience);
		return new ResponseEntity<>(new Message("Experiencia agregada"), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update (@PathVariable("id") int id, @RequestBody ExperienceDto dto){
		if (!service.existsById(id)) {// si no existe
			return new ResponseEntity<>(new Message("El id no existe"), HttpStatus.BAD_REQUEST);
		}
		if (service.existsByName(dto.getName()) && service.getByName(dto.getName()).get().getId() != id) {
			return new ResponseEntity<>(new Message("Esa experiencia ya existe"), HttpStatus.BAD_REQUEST);
		}
		if (StringUtils.isBlank(dto.getName())) {
			return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		}
		Experience experience = service.getOne(id).get();
		experience.setName(dto.getName());
		experience.setDescription(dto.getDescription());
		service.save(experience);
		return new ResponseEntity<>(new Message("Experiencia actualizada"), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		if (!service.existsById(id)) {// si no existe
			return new ResponseEntity<>(new Message("El id no existe"), HttpStatus.BAD_REQUEST);
		}
		service.delete(id);
	    return new ResponseEntity<>(new Message("Experiencia eliminada"), HttpStatus.OK);
	}
	
	@GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        if(!service.existsById(id))
            return new ResponseEntity<>(new Message("no existe"), HttpStatus.NOT_FOUND);
        Experience experience = service.getOne(id).get();
        return new ResponseEntity<>(experience, HttpStatus.OK);
    }
}
