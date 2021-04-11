package com.garagemanagement.service;

import java.util.List;

import com.garagemanagement.entity.History;

public interface HistoryService {
	 List<History> findAll ();
	 boolean insert (History history);
}
