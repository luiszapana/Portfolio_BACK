package com.luis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luis.entities.Person;

public interface IPersonRepository extends JpaRepository<Person, Long>{

}
