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

import com.luis.dto.HysDto;
import com.luis.entities.Hys;
import com.luis.security.controller.Message;
import com.luis.services.HysService;

@RestController
@RequestMapping("/skill")
@CrossOrigin (origins = "http://localhost:4200")
public class HysController {
	@Autowired
	private HysService service;
	
	@GetMapping("/list")
	public ResponseEntity<List<Hys>> list(){
		List<Hys> list = service.list();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        if(!service.existsById(id))
            return new ResponseEntity<>(new Message("no existe"), HttpStatus.NOT_FOUND);
        Hys hys = service.getOne(id).get();
        return new ResponseEntity<>(hys, HttpStatus.OK);
    }
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		if (!service.existsById(id)) {// si no existe
			return new ResponseEntity<>(new Message("El id no existe"), HttpStatus.BAD_REQUEST);
		}
		service.delete(id);
	    return new ResponseEntity<>(new Message("Skill eliminado"), HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody HysDto dto){
		if (StringUtils.isBlank(dto.getName())) {
			return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		}
		if (service.existsByName(dto.getName())) {
			return new ResponseEntity<>(new Message("Skill existente"), HttpStatus.BAD_REQUEST);
		}
		Hys hys = new Hys(dto.getName(), dto.getPercentage());
		service.save(hys);
		return new ResponseEntity<>(new Message("Skill agregado"), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update (@PathVariable("id") int id, @RequestBody HysDto dto){
		if (!service.existsById(id)) {// si no existe
			return new ResponseEntity<>(new Message("El id no existe"), HttpStatus.BAD_REQUEST);
		}
		if (service.existsByName(dto.getName()) && service.getByName(dto.getName()).get().getId() != id) {
			return new ResponseEntity<>(new Message("Skill existente"), HttpStatus.BAD_REQUEST);
		}
		if (StringUtils.isBlank(dto.getName())) {
			return new ResponseEntity<>(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		}
		Hys hys = service.getOne(id).get();
		hys.setName(dto.getName());
		hys.setPercentage(dto.getPercentage());
		service.save(hys);
		return new ResponseEntity<>(new Message("Skill actualizado"), HttpStatus.OK);
	}	
}
