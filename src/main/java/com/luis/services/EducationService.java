package com.luis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luis.entities.Education;
import com.luis.repositories.IEducationRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EducationService {
	@Autowired
	private IEducationRepository educationRepository;
	
	public List<Education> list(){
		return educationRepository.findAll();
	}
	
	public Optional<Education> getOne(int id){
		return educationRepository.findById(id);
	}
	
	public Optional<Education> getByName(String name){
		return educationRepository.findByName(name);
	}
	
	public void save(Education education) {
		educationRepository.save(education);
	}
	
	public void delete(int id) {
		educationRepository.deleteById(id);
	}
	
	public boolean existsById(int id) {
		return educationRepository.existsById(id);
	}
	
	public boolean existsByName(String name) {
		return educationRepository.existsByName(name);
	}
}
