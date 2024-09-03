package com.luis.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luis.entities.Experience;

public interface IExperienceRepository extends JpaRepository<Experience, Integer>{
	Optional<Experience> findByName(String name);
	boolean existsByName(String name);
}
