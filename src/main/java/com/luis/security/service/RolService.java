package com.luis.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luis.security.entity.Rol;
import com.luis.security.entity.repository.IRolRepository;
import com.luis.security.enums.RolName;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolService {
	@Autowired
	private IRolRepository rolRepository;
	
	public Optional<Rol> getByRolName(RolName rolName){
		return rolRepository.findByRolName(rolName);
	}
	
	public void save(Rol rol) {
		rolRepository.save(rol);
	}
}
