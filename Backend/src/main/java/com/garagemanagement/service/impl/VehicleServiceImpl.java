package com.garagemanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garagemanagement.entity.Person;
import com.garagemanagement.entity.Vehicle;
import com.garagemanagement.repository.HistoryRepository;
import com.garagemanagement.repository.PersonRepository;
import com.garagemanagement.repository.VehicleRepository;
import com.garagemanagement.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private HistoryRepository historyRepository;

	@Override
	public boolean check(Vehicle vehicle) {
		Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicle.getNumberPlate());
		if (optionalVehicle.isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	public List<Vehicle> findAll() {
		List<Vehicle> vehicles = vehicleRepository.findAll();
		return vehicles;
	}

	@Override
	public boolean insert(Vehicle vehicle) {
		Optional<Person> optionalPerson = personRepository.findById(vehicle.getPerson().getId());
		if (optionalPerson.isEmpty()) {
			return false;
		}

		Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicle.getNumberPlate());
		if (optionalVehicle.isPresent()) {
			throw new RuntimeException("Duplicate vehicle");
		}

		vehicle.setPerson(optionalPerson.get());
		vehicleRepository.save(vehicle);
		return true;
	}

	@Override
	public boolean update(Vehicle vehicle) {
		Optional<Person> optionalPerson = personRepository.findById(vehicle.getPerson().getId());
		Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicle.getNumberPlate());
		if (optionalVehicle.isEmpty()) {
			return false;
		}

		vehicle.setPerson(optionalPerson.get());
		vehicleRepository.save(vehicle);
		return true;
	}

	@Override
	public boolean delete(Vehicle vehicle) {
//		historyRepository.deleteByVehicleId(vehicle.getNumberPlate());
//		vehicleRepository.delete(vehicle.getNumberPlate());
		return true;
	}
}
