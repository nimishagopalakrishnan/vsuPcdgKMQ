package com.codeTest.weather.service.impl;

import static com.codeTest.weather.model.Constants.MESSAGE;
import static com.codeTest.weather.model.Constants.MONTH;
import static com.codeTest.weather.model.Constants.RESULT;
import static com.codeTest.weather.model.Constants.TODAY;
import static com.codeTest.weather.model.Constants.WEEK;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codeTest.weather.entity.Metrics;
import com.codeTest.weather.entity.Sensor;
import com.codeTest.weather.model.AverageMetricsResponse;
import com.codeTest.weather.repo.MetricsRepo;
import com.codeTest.weather.repo.SensorRepo;
import com.codeTest.weather.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	SensorRepo sensorRepo;

	@Autowired
	MetricsRepo metricsRepo;

	@Transactional
	@Override
	public ResponseEntity<HashMap<String, Object>> registerSensor(Sensor sensorInfo) {
		HttpStatus status;

		HashMap<String, Object> response = new HashMap<String, Object>();
		if (sensorInfo != null) {
			if (sensorRepo.findById(sensorInfo.getSensorId()).isEmpty()) {
				try {
					Sensor savedSensor = sensorRepo.save(sensorInfo);
					status = HttpStatus.OK;
					response.put(MESSAGE, String.format("Sensor with id %d registered", sensorInfo.getSensorId()));
					response.put(RESULT, savedSensor);
				}

				catch (IllegalArgumentException exp) {
					status = HttpStatus.FORBIDDEN;
					response.put(MESSAGE, String.format("Error registering sensor"));
				}
			} else {
				status = HttpStatus.BAD_REQUEST;
				response.put(MESSAGE, String.format("Sensor with id %d exists. Please register with different id",
						sensorInfo.getSensorId()));
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
			response.put(MESSAGE, "Please enter right sensor information to register");
		}
		return new ResponseEntity<HashMap<String, Object>>(response, status);
	}

	@Transactional
	@Override
	public ResponseEntity<HashMap<String, Object>> addSensorMetrics(Metrics metricsInfo) {
		HttpStatus status;
		HashMap<String, Object> response = new HashMap<String, Object>();

		if (metricsInfo != null && metricsInfo.getSensor() != null) {
			if (!sensorRepo.findById(metricsInfo.getSensor().getSensorId()).isEmpty()) {
				try {
					Metrics savedMetrics = metricsRepo.save(metricsInfo);
					status = HttpStatus.OK;
					response.put(MESSAGE, String.format("Metrics with id %d for sensor id %d added",
							savedMetrics.getMetricId(), savedMetrics.getSensor().getSensorId()));
					response.put(RESULT, savedMetrics);
				} catch (IllegalArgumentException exp) {
					status = HttpStatus.FORBIDDEN;
					response.put(MESSAGE, String.format("Error adding metrics"));
				}
			} else {
				status = HttpStatus.BAD_REQUEST;
				response.put(MESSAGE, String.format("Sensor does not exist. Please register sensor first",
						metricsInfo.getMetricId()));
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
			response.put(MESSAGE, "Please enter right metrics information to register");
		}
		return new ResponseEntity<HashMap<String, Object>>(response, status);
	}

	@Transactional
	@Override
	public ResponseEntity<HashMap<String, Object>> getAverageMetricsForSensors(ArrayList<Integer> sensorIds,
			String dateRange) {
		HttpStatus status;
		HashMap<String, Object> response = new HashMap<String, Object>();
		Date toDate = new Date();
		Date fromDate = null;
		if (dateRange.equals(WEEK)) {
			fromDate = Date.from(ZonedDateTime.now().minusDays(7).toInstant());
		} else if (dateRange.equals(MONTH)) {
			fromDate = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
		} else if (dateRange.equals(TODAY)) {
			fromDate = Date.from(ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant());
		}
		List<AverageMetricsResponse> averageList = metricsRepo.findAverageMetricsForSensors(sensorIds, fromDate,
				toDate);
		if (averageList.size() > 0) {
			status = HttpStatus.OK;
			response.put(MESSAGE, String.format("Average metrics for sensors for date range: %s", dateRange));
			response.put(RESULT, averageList);
		} else {
			status = HttpStatus.OK;
			response.put(MESSAGE, "No metrics available for sensors");
			response.put(RESULT, averageList);
		}

		return new ResponseEntity<HashMap<String, Object>>(response, status);
	}

	
}
