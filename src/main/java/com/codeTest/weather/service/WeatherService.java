package com.codeTest.weather.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;

import com.codeTest.weather.entity.Metrics;
import com.codeTest.weather.entity.Sensor;

public interface WeatherService {
	
	ResponseEntity<HashMap<String, Object>> registerSensor(Sensor sensor);
	
	ResponseEntity<HashMap<String, Object>> addSensorMetrics(Metrics metrics);

	ResponseEntity<HashMap<String, Object>> getAverageMetricsForSensors(ArrayList<Integer> sensorIds, String dateRange);

	
}
