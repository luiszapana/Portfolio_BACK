package com.luis.interfaces;

import java.util.List;

import com.luis.entities.Person;

public interface IPersonService {
	List<Person> getPersons();
	void savePerson(Person person);
	void deletePerson(Long id);
	Person findPerson(Long id);
}
