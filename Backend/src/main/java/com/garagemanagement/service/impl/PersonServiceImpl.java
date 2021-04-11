package com.garagemanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garagemanagement.entity.Person;
import com.garagemanagement.repository.PersonRepository;
import com.garagemanagement.repository.VehicleRepository;
import com.garagemanagement.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Override
	public List<Person> findAll() {
		List<Person> persons = personRepository.findAll();
		return persons;
	}

	@Override
	public boolean insert(Person person) {
		Optional<Person> optionalPerson = personRepository.findById(person.getId());
		if (optionalPerson.isPresent()) {
			return false;
		}

		personRepository.save(person);
		return true;
	}

	@Override
	public boolean update(Person person) {
		Optional<Person> optionalPerson = personRepository.findById(person.getId());
		if (optionalPerson.isEmpty()) {
			return false;
		}
		personRepository.save(person);
		return true;
	}

	@Override
	public boolean delete(Person person) { // TODO
//		vehicleRepository.deleteByPersonID(person.getId());
//		personRepository.deleteById(person.getId());
		return true;
	}
}
