package com.luis.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luis.entities.Person;
import com.luis.interfaces.IPersonService;
import com.luis.repositories.IPersonRepository;

@Service
public class PersonServiceImp implements IPersonService{
	
	@Autowired
	private IPersonRepository personRepository;
	
	@Override
	public List<Person> getPersons() {
		List<Person> persons = personRepository.findAll();
		return persons;
	}

	@Override
	public void savePerson(Person person) {
		personRepository.save(person);		
	}

	@Override
	public void deletePerson(Long id) {
		personRepository.deleteById(id);		
	}

	@Override
	public Person findPerson(Long id) {
		Person person = personRepository.findById(id).orElse(null);
		return person;
	}

}
