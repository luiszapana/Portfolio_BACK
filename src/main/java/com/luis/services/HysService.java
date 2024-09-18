package com.luis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luis.entities.Hys;
import com.luis.repositories.IHysRepository;

@Service
public class HysService {
	@Autowired
	private IHysRepository hysRepository;
	
	public List<Hys> list(){
		return hysRepository.findAll();
	}
	
	public Optional<Hys> getOne(int id){
		return hysRepository.findById(id);
	}
	
	public Optional<Hys> getByName(String name){
		return hysRepository.findByName(name);
	}
	
	public void save(Hys hys) {
		hysRepository.save(hys);
	}
	
	public void delete(int id) {
		hysRepository.deleteById(id);
	}
	
	public boolean existsById(int id) {
		return hysRepository.existsById(id);
	}
	
	public boolean existsByName(String name) {
		return hysRepository.existsByName(name);
	}
}
