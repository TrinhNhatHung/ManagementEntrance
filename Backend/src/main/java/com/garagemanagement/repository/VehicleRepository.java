package com.garagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.garagemanagement.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
//	
//	void delete(String numberPlate);
//	
//	@Modifying
//	@Query(value = "DELETE FROM Vehicle WHERE person_id = :personId", nativeQuery = true)
//	@Transactional
//	void deleteByPersonID (@Param("personId") Long personId);
}
