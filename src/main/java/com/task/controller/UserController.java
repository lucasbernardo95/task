package com.task.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.task.model.User;
import com.task.repository.UserRepository;
import com.task.response.Response;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@GetMapping(produces = "application/json")
	public @ResponseBody Iterable<User> listAll() {
		Iterable<User> list = repository.findAll();
		return list;
	}
	
	@GetMapping(path = "/{id}",produces = "application/json")
	public ResponseEntity<Response<User>> getById(@PathVariable Integer id) {
		Response<User> response = new Response<>();
		
		if(validateId(id, response)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<User> user = repository.findById(id);
		if (user.isPresent()) {
			response.setData(user.get());
			return ResponseEntity.ok(response);

		}
		response.getErrors().add("Usuário não encontrado.");
		return ResponseEntity.badRequest().body(response);
	}
	
	@PostMapping(produces = "application/json")
	public User addOrUpdate(@RequestBody @Valid User user) {
		Optional<User> userUpdate = null;
		
		if(user.getId() != null && user.getId() <= 0) 
			return null;
		else if(user.getId() != null && user.getId() > 0) {
			userUpdate = repository.findById(user.getId());
			if(!userUpdate.isPresent())
				return null;
			user = userUpdate.get();
		}
			
		return repository.save(user);
	}
	
	@DeleteMapping()
	public User delete(@RequestBody User user) {
		repository.delete(user);
		return user;
	}
	
	@PostMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Response<User>> deleteById(@PathVariable("id") Integer id) {

		Response<User> response = new Response<>();
		
		if(validateId(id, response)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<User> user = repository.findById(id);
		if (user.isPresent()) {

			repository.delete(user.get());
			response.setData(user.get());
			return ResponseEntity.ok(response);

		}
		response.getErrors().add("Usuário não encontrado.");
		return ResponseEntity.badRequest().body(response);
	}
	
	private boolean validateId(Integer id, Response<User> response) {
		if(id == null || id <= 0) {
			response.getErrors().add("ID invalid!");
			return true;
		}
		return false;
	}
}
