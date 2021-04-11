package com.garagemanagement.service;

import java.util.List;

import com.garagemanagement.entity.Vehicle;

public interface VehicleService {
	List<Vehicle> findAll ();
	boolean insert (Vehicle vehicle);
	boolean update (Vehicle vehicle);
	boolean delete (Vehicle vehicle);
	boolean check (Vehicle vehicle);
}
