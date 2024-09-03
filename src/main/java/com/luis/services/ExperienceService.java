package com.luis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luis.entities.Experience;
import com.luis.repositories.IExperienceRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ExperienceService {
	@Autowired
	private IExperienceRepository experienceRepo;
	
	public List<Experience> list(){
		return experienceRepo.findAll();
	}
	
	public Optional<Experience> getOne(int id){
		return experienceRepo.findById(id);
	}
	
	public Optional<Experience> getByName(String name){
		return experienceRepo.findByName(name);
	}
	
	public void save(Experience experience) {
		experienceRepo.save(experience);
	}
	
	public void delete(int id) {
		experienceRepo.deleteById(id);
	}
	
	public boolean existsById(int id) {
		return experienceRepo.existsById(id);
	}
	
	public boolean existsByName(String name) {
		return experienceRepo.existsByName(name);
	}
}
