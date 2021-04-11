package com.garagemanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.garagemanagement.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
	
	@Modifying
	@Query(value = "DELETE FROM History WHERE vehicle_id = :numberPlate", nativeQuery = true)
	@Transactional
	void deleteByVehicleId (@Param("numberPlate") String numberPlate);
}