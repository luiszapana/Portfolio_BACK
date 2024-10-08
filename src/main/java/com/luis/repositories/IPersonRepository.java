package com.luis.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luis.entities.Person;

public interface IPersonRepository extends JpaRepository<Person, Long>{
	Optional<Person> findByName(String name);
	boolean existsByName(String name);
}
