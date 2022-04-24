package com.codeTest.weather.controller;

import static com.codeTest.weather.model.Constants.MESSAGE;
import static com.codeTest.weather.model.Constants.MONTH;
import static com.codeTest.weather.model.Constants.TODAY;
import static com.codeTest.weather.model.Constants.WEEK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codeTest.weather.entity.Metrics;
import com.codeTest.weather.entity.Sensor;
import com.codeTest.weather.service.WeatherService;

@RestController 
public class WeatherController {
	@Autowired
	WeatherService weatherService;

	@PostMapping("/registerSensor")
	public ResponseEntity<HashMap<String,Object>> registerSensor(@RequestBody Sensor sensorInfo) {
		ResponseEntity<HashMap<String,Object>> responseStatus = null;
		responseStatus=weatherService.registerSensor(sensorInfo);		
		return responseStatus;
		
	}
	
	@PostMapping("/addSensorMetrics")
	public ResponseEntity<HashMap<String,Object>> addSensorMetrics(@RequestBody Metrics metricsInfo) {
		ResponseEntity<HashMap<String,Object>> responseStatus = null;
		responseStatus= weatherService.addSensorMetrics(metricsInfo);
		return responseStatus;
		
	}
	
	@GetMapping("/getAverageMetricsForSensor")
	public ResponseEntity<HashMap<String,Object>> getAverageMetricsForSensors(@RequestParam(required = true) ArrayList<Integer> sensorIds,
			@RequestParam(defaultValue = TODAY) String dateRange
			) {
		ArrayList<String> dateTypes = new ArrayList<String>(Arrays.asList(WEEK, MONTH, TODAY));
		ResponseEntity<HashMap<String,Object>> responseStatus = null;
		
		if(sensorIds!=null && !sensorIds.isEmpty() && dateRange!=null && !dateRange.isEmpty() && dateTypes.contains(dateRange.toLowerCase())) {
			responseStatus= weatherService.getAverageMetricsForSensors(sensorIds,dateRange.toLowerCase());
		}
		else {
			HashMap<String,Object> response = new HashMap<String, Object>();
			HttpStatus status=HttpStatus.BAD_REQUEST;
			response.put(MESSAGE,"Please enter right information to search");
			responseStatus= new ResponseEntity<HashMap<String,Object>>(response,status);
		}
		return responseStatus;
			
	}
}
