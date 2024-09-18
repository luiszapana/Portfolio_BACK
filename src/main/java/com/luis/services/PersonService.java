package com.luis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luis.entities.Person;
import com.luis.repositories.IPersonRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PersonService{
	@Autowired
	private IPersonRepository personRepository;

	public List<Person> list(){
		return personRepository.findAll();
	}
	
	public Optional<Person> getOne(Long id){
		return personRepository.findById(id);
	}
	
	public Optional<Person> getByName(String name){
		return personRepository.findByName(name);
	}
	
	public void save(Person person) {
		personRepository.save(person);
	}
	
	public void delete(Long id) {
		personRepository.deleteById(id);
	}
	
	public boolean existsById(Long id) {
		return personRepository.existsById(id);
	}
	
	public boolean existsByName(String name) {
		return personRepository.existsByName(name);
	}
}
