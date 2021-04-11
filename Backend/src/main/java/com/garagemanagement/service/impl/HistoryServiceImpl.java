package com.garagemanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garagemanagement.entity.History;
import com.garagemanagement.repository.HistoryRepository;
import com.garagemanagement.service.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService {
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Override
	public List<History> findAll() {
		List<History> histories = historyRepository.findAll();
		return histories;
	}

	@Override
	public boolean insert(History history) {
	    historyRepository.save(history);
		return true;
	}
}
