package com.luis.security.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luis.security.dto.JwtDto;
import com.luis.security.dto.LoginUser;
import com.luis.security.dto.NewUser;
import com.luis.security.entity.Rol;
import com.luis.security.entity.User;
import com.luis.security.enums.RolName;
import com.luis.security.jwt.JwtProvider;
import com.luis.security.service.RolService;
import com.luis.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	//A diferencia del profe, yo los defini como privates
	@Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private RolService rolService;
    @Autowired
    private JwtProvider jwtProvider;
	
	@PostMapping("/new")
	public ResponseEntity<?> nuevo(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Message("Campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
		}
		if(userService.existsByUsername(newUser.getUsername())) {
			return new ResponseEntity<>(new Message("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
		}
		if(userService.existsByEmail(newUser.getEmail())) {
			return new ResponseEntity<>(new Message("Ese email ya existe"), HttpStatus.BAD_REQUEST);
		}
		User user = new User(newUser.getName(), newUser.getUsername(), newUser.getEmail()
				, passwordEncoder.encode(newUser.getPassword()));
		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolName(RolName.ROLE_USER).get());
		
		if (newUser.getRoles().contains("admin")) {
			roles.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
		}
			
		user.setRoles(roles);
		userService.save(user);
		
		return new ResponseEntity<>(new Message("Usuario guardado"), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login (@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Message("Campos mal puestos"), HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String username = ((UserDetails) authentication.getPrincipal()).getUsername(); // Extrae username del objeto Authentication
		String jwt = jwtProvider.generateToken(username);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		
		return new ResponseEntity<>(jwtDto, HttpStatus.OK);
	}
	
}
