package com.garagemanagement.api;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.garagemanagement.entity.History;
import com.garagemanagement.entity.Vehicle;
import com.garagemanagement.service.HistoryService;

@CrossOrigin
@RestController
@RequestMapping(value = "/history")
public class HistoryAPI {
	
	@Value("${project.path}")
	private String PATH;

	@Autowired
	private HistoryService historyService;

	private static Map<String, String> statusResponse = new HashMap<>();

	@PostMapping(value = "/add")
	public ResponseEntity<Map<String, String>> postImage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "image", required = true) MultipartFile multipartFile,
			@RequestParam(value = "is_out", required = false) boolean isOut,
			@RequestParam(value = "vehicle_id", required = true) String numberPlate,
			@RequestParam(value = "time", required = true) Timestamp time) {
		String fileName = multipartFile.getOriginalFilename();
		fileName = time.getTime() + fileName.substring(fileName.lastIndexOf("."));
		String rootFile = PATH;
		File file = new File(rootFile + File.separator + "HistoryImage", fileName);
		try {
			multipartFile.transferTo(file);
			Vehicle vehicle = Vehicle.builder().numberPlate(numberPlate).build();
			History history = History.builder().vehicle(vehicle)
											   .isOut(false)
											   .time(time)
											   .image(fileName)
											   .build();
			boolean status = historyService.insert(history);
			statusResponse.put("message", "Successfully");
		} catch (Exception e) {
			statusResponse.put("message", "Failed");
		}
		
		return new ResponseEntity<Map<String, String>>(statusResponse, HttpStatus.OK);
	}

	@PostMapping(value = "/all")
	public ResponseEntity<List<History>> getHistoryAll(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "date_from", required = false) Date dateFrom,
			@RequestParam(name = "date_to", required = false) Date dateTo,
			@RequestParam(name = "time_from", required = false) Time timeFrom,
			@RequestParam(name = "time_to", required = false) Time timeTo,
			@RequestParam(name = "number_plate", required = false) String numberPlate,
			@RequestParam(name = "is_out", required = false) Boolean isOut) {
		List<History> histories = historyService.findAll();
		if (dateFrom != null) {
			LocalDate dateFromLocalDate = LocalDate.of(dateFrom.getYear(), dateFrom.getMonth(), dateFrom.getDate());
			histories = getHistoryByConditionDate(histories, dateFromLocalDate, () -> true);
		}

		if (dateTo != null) {
			LocalDate dateToLocalDate = LocalDate.of(dateTo.getYear(), dateTo.getMonth(), dateTo.getDate());
			histories = getHistoryByConditionDate(histories, dateToLocalDate, () -> false);
		}

		if (timeFrom != null) {
			LocalTime timeFromLocalTime = LocalTime.of(timeFrom.getHours(), timeFrom.getMinutes(),
					timeFrom.getSeconds());
			histories = getHistoryByConditionTime(histories, timeFromLocalTime, () -> true);
		}

		if (timeTo != null) {
			LocalTime timeToLocalTime = LocalTime.of(timeTo.getHours(), timeTo.getMinutes(), timeTo.getSeconds());
			histories = getHistoryByConditionTime(histories, timeToLocalTime, () -> false);
		}

		if (numberPlate != null) {
			histories = histories.stream().filter(history -> history.getVehicle().getNumberPlate().equals(numberPlate))
					.collect(Collectors.toList());
		}

		if (isOut != null) {
			histories = histories.stream().filter(history -> history.isOut() == isOut).collect(Collectors.toList());
		}

		histories.sort(Comparator.comparing(History::getTime, Comparator.reverseOrder()));
		return new ResponseEntity<List<History>>(histories, HttpStatus.OK);
	}

	private List<History> getHistoryByConditionDate(List<History> histories, LocalDate dateCondition,
			BeforeOrAfter beforeOrAfter) {
		List<History> result = histories.stream().filter(history -> {
			Timestamp timestamp = history.getTime();
			LocalDate historyLocalDate = LocalDate.of(timestamp.getYear(), timestamp.getMonth(), timestamp.getDate());
			boolean isAfter = false;
			if (beforeOrAfter.isAfter()) {
				isAfter = true;
			}
			if (isAfter == true) {
				if (historyLocalDate.isAfter(dateCondition) || historyLocalDate.isEqual(dateCondition)) {
					return true;
				}
			} else {
				if (historyLocalDate.isBefore(dateCondition) || historyLocalDate.isEqual(dateCondition)) {
					return true;
				}
			}
			return false;
		}).collect(Collectors.toList());
		return result;
	}

	private List<History> getHistoryByConditionTime(List<History> histories, LocalTime timeCondition,
			BeforeOrAfter beforeOrAfter) {
		List<History> result = histories.stream().filter(history -> {
			Timestamp timestamp = history.getTime();
			LocalTime historyLocalTime = LocalTime.of(timestamp.getHours(), timestamp.getMinutes(),
					timestamp.getSeconds());
			boolean isAfter = false;
			if (beforeOrAfter.isAfter()) {
				isAfter = true;
			}
			if (isAfter == true) {
				if (historyLocalTime.isAfter(timeCondition)) {
					return true;
				}
			} else {
				if (historyLocalTime.isBefore(timeCondition)) {
					return true;
				}
			}
			return false;
		}).collect(Collectors.toList());
		return result;
	}

	public interface BeforeOrAfter {
		boolean isAfter();
	}
}