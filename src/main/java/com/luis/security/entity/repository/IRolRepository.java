package com.luis.security.entity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luis.security.entity.Rol;
import com.luis.security.enums.RolName;

public interface IRolRepository extends JpaRepository<Rol, Integer>{
	Optional<Rol> findByRolName(RolName rolName);
	
}
