package com.garagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garagemanagement.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

//	void deleteById(Long id);
}
