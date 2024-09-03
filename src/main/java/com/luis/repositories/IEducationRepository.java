package com.luis.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luis.entities.Education;

public interface IEducationRepository extends JpaRepository<Education, Integer>{
	Optional<Education> findByName(String name);
	boolean existsByName(String name);
}
