package com.garagemanagement.service;

import java.util.List;

import com.garagemanagement.entity.Person;

public interface PersonService {
	List<Person> findAll ();
	boolean insert (Person person);
	boolean update (Person person);
	boolean delete (Person person);
}
