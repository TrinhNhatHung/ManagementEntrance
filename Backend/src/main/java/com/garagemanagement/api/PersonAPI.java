package com.garagemanagement.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garagemanagement.entity.Person;
import com.garagemanagement.service.PersonService;

@CrossOrigin
@RestController
@RequestMapping(value = "person")
public class PersonAPI {
	
	@Autowired
	private PersonService personService;
	
	private static Map<String, String> statusReposnse = new HashMap<>();
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Person>> getAll(HttpServletRequest request) {
		List<Person> vehicles = personService.findAll();
		return new ResponseEntity<List<Person>>(vehicles,HttpStatus.OK);
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<Map<String, String>> register(HttpServletRequest request,
			@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "phone_number", required = true) String phoneNumber, 
			@RequestParam(name = "gender", required = true) String gender,
			@RequestParam(name = "email", required = true) String email) {
		Person person = Person.builder().name(name)
										.phoneNumber(phoneNumber)
										.email(email)
										.gender(gender)
										.id(id)
										.build();
		boolean status = false;
		try {
			status = personService.insert(person);
		} catch (Exception e) {
			 statusReposnse.put("message", "Failed");
			 return new ResponseEntity<>(statusReposnse, HttpStatus.EXPECTATION_FAILED);
		} 
		
		if (status) {
			statusReposnse.put("message", "Successfully");
			return new ResponseEntity<>(statusReposnse, HttpStatus.OK);
		} else {
			statusReposnse.put("message", "Duplicate");
			return new ResponseEntity<>(statusReposnse, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PostMapping(value = "/update")
	public ResponseEntity<Map<String, String>> update(HttpServletRequest request,
			@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "phone_number", required = true) String phoneNumber, 
			@RequestParam(name = "gender", required = true) String gender,
			@RequestParam(name = "email", required = true) String email) {
		Person person = Person.builder().id(id)
										.email(email)
										.gender(gender)
										.name(name)
										.phoneNumber(phoneNumber)
										.build();
		boolean status = false;
		try {
			status = personService.update(person);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!status) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/delete")
	public ResponseEntity<Map<String, String>> delete(HttpServletRequest request,
			@RequestParam(name = "id", required = true) Long id) {
		Person person = Person.builder().id(id).build();
		try {
			personService.delete(person);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
