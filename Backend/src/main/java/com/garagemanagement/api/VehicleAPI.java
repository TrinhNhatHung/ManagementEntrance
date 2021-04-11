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
import com.garagemanagement.entity.Vehicle;
import com.garagemanagement.service.VehicleService;

@CrossOrigin
@RestController
@RequestMapping(value = "vehicle")
public class VehicleAPI {

	@Autowired
	private VehicleService vehicleService;

	private static Map<String, String> statusReposnse = new HashMap<>();
	
	@GetMapping(value = "/check-invalid")
	public ResponseEntity<Map<String, String>> checkInvalidVehicle(
			@RequestParam(name = "number_plate", required = true) String numberPlate) {
		Vehicle vehicle = Vehicle.builder().numberPlate(numberPlate).build();
		boolean status = vehicleService.check(vehicle);
		if (status) {
			return new ResponseEntity<Map<String, String>>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Map<String, String>>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Vehicle>> getAll(HttpServletRequest request) {
		List<Vehicle> vehicles = vehicleService.findAll();
		return new ResponseEntity<List<Vehicle>>(vehicles,HttpStatus.OK);
	}

	@PostMapping(value = "/register")
	public ResponseEntity<Map<String, String>> register(HttpServletRequest request,
			@RequestParam(name = "number_plate", required = true) String numberPlate,
			@RequestParam(name = "car_manufacturer", required = true) String carManufacturer,
			@RequestParam(name = "name_vehicle", required = true) String nameVehicle,
			@RequestParam(name = "person_id", required = true) Long personId,
			@RequestParam(name = "color", required = true) String color) {
		Person person = Person.builder().id(personId).build();
		Vehicle vehicle = Vehicle.builder().numberPlate(numberPlate).carManufacturer(carManufacturer)
				.nameVehicle(nameVehicle).person(person).color(color).build();
		boolean status = false;
		try {
			status = vehicleService.insert(vehicle);
		} catch (Exception e) {
			statusReposnse.put("message", "Duplicate-Vehicle");
			return new ResponseEntity<Map<String, String>>(statusReposnse, HttpStatus.EXPECTATION_FAILED);
		}

		if (status) {
			statusReposnse.put("message", "Successfully");
			return new ResponseEntity<Map<String, String>>(statusReposnse, HttpStatus.OK);
		} else {
			statusReposnse.put("message", "Non-IDNumber");
			return new ResponseEntity<Map<String, String>>(statusReposnse, HttpStatus.EXPECTATION_FAILED);
		}	
	}

	@PostMapping(value = "/update")
	public ResponseEntity<Map<String, String>> updateVehicle (HttpServletRequest request,
							@RequestParam(name="number_plate", required = true) String numberPlate,
							@RequestParam(name="car_manufacturer", required = true) String carManufacturer,
							@RequestParam(name="name_vehicle", required = true) String nameVehicle,
							@RequestParam(name="color", required = true) String color,
							@RequestParam(name="person_id", required = true) Long personId) {
		Person person = Person.builder().id(personId).build();
		Vehicle vehicle = Vehicle.builder().carManufacturer(carManufacturer)
										   .color(color)
										   .nameVehicle(nameVehicle)
										   .person(person)
										   .numberPlate(numberPlate)
										   .build();
		
		boolean status = false;
		
		try {
			status = vehicleService.update(vehicle);
		} catch (Exception e) {
			e.printStackTrace();  // Null person exception
		}
		if (!status) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED); // Null vehicle
		}
		return new ResponseEntity<Map<String, String>>(statusReposnse, HttpStatus.OK);
	}
	
	@PostMapping(value = "/delete")
	public ResponseEntity<Map<String, String>> deleteVehicle(HttpServletRequest request,
			@RequestParam(name = "number_plate", required = true) String numberPlate) {
		
		Vehicle vehicle = Vehicle.builder().numberPlate(numberPlate).build();
		try {
		    vehicleService.delete(vehicle);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(statusReposnse, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>( HttpStatus.OK);
	}

}
