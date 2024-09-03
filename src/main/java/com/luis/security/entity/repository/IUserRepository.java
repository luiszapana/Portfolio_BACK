package com.luis.security.entity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luis.security.entity.User;

public interface IUserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
