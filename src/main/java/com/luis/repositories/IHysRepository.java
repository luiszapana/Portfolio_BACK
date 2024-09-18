package com.luis.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luis.entities.Hys;

public interface IHysRepository extends JpaRepository<Hys, Integer>{
	Optional<Hys> findByName(String name);
	boolean existsByName(String name);
}
