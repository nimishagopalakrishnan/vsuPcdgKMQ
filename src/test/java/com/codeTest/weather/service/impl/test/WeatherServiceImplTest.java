package com.codeTest.weather.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codeTest.weather.entity.Metrics;
import com.codeTest.weather.entity.Sensor;
import com.codeTest.weather.model.AverageMetricsResponse;
import com.codeTest.weather.repo.MetricsRepo;
import com.codeTest.weather.repo.SensorRepo;
import com.codeTest.weather.service.impl.WeatherServiceImpl;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import static com.codeTest.weather.model.Constants.RESULT;

@SpringBootTest
public class WeatherServiceImplTest {
	
	@Mock
	SensorRepo sensorRepo;
	
	@Mock
	MetricsRepo metricsRepo;
	
	@InjectMocks
	WeatherServiceImpl weatherServiceImpl;
	
	private Sensor sensorInfoTest;
	private Metrics metricsInfoTest;
	
	@BeforeEach()
	void setup() {
		sensorInfoTest = new Sensor(1, "Ireland", "Dublin", new HashSet<Metrics>());
		metricsInfoTest = new Metrics(1, sensorInfoTest, new Date(), 11.0f, 8.2f, 23.0f);
	}
	
	@Test
	public void registerSensorServiceSuccessTest() {
		when(sensorRepo.findById(any(Integer.class))).thenReturn(Optional.empty());
		when(sensorRepo.save(any(Sensor.class))).thenReturn(any(Sensor.class));
		
		ResponseEntity<HashMap<String,Object>> response = weatherServiceImpl.registerSensor(sensorInfoTest);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void registerSensorServiceExceptionTest() {
	
		Sensor sensorTestObject= new Sensor();
		sensorTestObject.setCountryName("Ireland");
		when(sensorRepo.findById(any())).thenReturn(Optional.empty());
		when(sensorRepo.save(sensorTestObject)).thenThrow(new IllegalArgumentException());
		
		ResponseEntity<HashMap<String,Object>> response = weatherServiceImpl.registerSensor(sensorTestObject);
		
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void registerSensorServiceBadRequestTest() {
		Optional<Sensor> sensorTestObject = Optional.of(sensorInfoTest);
		when(sensorRepo.findById(any())).thenReturn(sensorTestObject);
		
		ResponseEntity<HashMap<String,Object>> response = weatherServiceImpl.registerSensor(sensorInfoTest);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		response = weatherServiceImpl.registerSensor(null);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}
	
	@Test
	public void addSensorMetricsServiceSuccessTest() {
		Optional<Sensor> sensorTestObject = Optional.of(sensorInfoTest);
		when(sensorRepo.findById(any(Integer.class))).thenReturn(sensorTestObject);
		when(metricsRepo.save(metricsInfoTest)).thenReturn(metricsInfoTest);
		
		ResponseEntity<HashMap<String,Object>> response = weatherServiceImpl.addSensorMetrics(metricsInfoTest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void addSensorMetricsServiceExceptionTest() {
		Optional<Sensor> sensorTestObject = Optional.of(sensorInfoTest);
		when(sensorRepo.findById(any())).thenReturn(sensorTestObject);
		when(metricsRepo.save(metricsInfoTest)).thenThrow(new IllegalArgumentException());
		
		ResponseEntity<HashMap<String,Object>> response = weatherServiceImpl.addSensorMetrics(metricsInfoTest);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void addSensorMetricsServiceBadRequestTest() {
		when(sensorRepo.findById(any())).thenReturn(Optional.empty());
		
		ResponseEntity<HashMap<String,Object>> response = weatherServiceImpl.addSensorMetrics(metricsInfoTest);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		metricsInfoTest.setSensor(null);
		response = weatherServiceImpl.addSensorMetrics(null);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		response = weatherServiceImpl.addSensorMetrics(null);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}
	
	@Test
	public void getAverageMetricsForSensorsSuccessTest() {
		List<AverageMetricsResponse> averageListResponse = new ArrayList<AverageMetricsResponse>();
		when(metricsRepo.findAverageMetricsForSensors(any(), any(), any())).thenReturn(averageListResponse);
		ResponseEntity<HashMap<String,Object>> response = weatherServiceImpl.getAverageMetricsForSensors(new ArrayList<Integer>(), "week");
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("No metrics available for sensors", response.getBody().get("message"));
		
		averageListResponse.add(0, null);
		when(metricsRepo.findAverageMetricsForSensors(any(), any(), any())).thenReturn(averageListResponse);
		response = weatherServiceImpl.getAverageMetricsForSensors(new ArrayList<Integer>(), "month");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Average metrics for sensors for date range: month", response.getBody().get("message"));
		
		response = weatherServiceImpl.getAverageMetricsForSensors(new ArrayList<Integer>(), "today");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	
}
