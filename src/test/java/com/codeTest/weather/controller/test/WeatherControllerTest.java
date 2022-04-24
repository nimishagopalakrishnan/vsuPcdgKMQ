package com.codeTest.weather.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.codeTest.weather.controller.WeatherController;
import com.codeTest.weather.entity.Metrics;
import com.codeTest.weather.entity.Sensor;
import com.codeTest.weather.service.WeatherService;

@SpringBootTest
public class WeatherControllerTest {
	
	@Mock
	WeatherService weatherService;
	
	@InjectMocks
	private WeatherController weatherController;

	
	private Sensor sensorInfo;
	private Metrics metricsInfo;
	
	@BeforeEach
	void setUp() {
		sensorInfo = new Sensor(1,"Ireland","Dublin", null);
		metricsInfo = new Metrics(1, sensorInfo, new Date(), 11.0f, 8.2f, 23.0f);
	 }
	
	
	@Test
	public void registerSensorTest() {

		when(weatherService.registerSensor(any(Sensor.class))).thenReturn(new ResponseEntity<HashMap<String,Object>>(new HashMap<String, Object>() ,HttpStatus.OK));
		ResponseEntity<HashMap<String,Object>>result =  weatherController.registerSensor(sensorInfo);
		
		assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
		
	}
	
	@Test
	public void addSensorMetricsTest() {
		when(weatherService.addSensorMetrics(any(Metrics.class))).thenReturn(new ResponseEntity<HashMap<String,Object>>(new HashMap<String, Object>() ,HttpStatus.OK));
		ResponseEntity<HashMap<String,Object>>result =  weatherController.addSensorMetrics(metricsInfo);
		
		assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
		
	}
	
	
	@Test
	public void getAverageMetricsForSensorsSuccessTest() {
		ArrayList<Integer> sensorIds = new ArrayList<Integer>(Arrays.asList(1,2));
		
		when(weatherService.getAverageMetricsForSensors(sensorIds, "week")).thenReturn(new ResponseEntity<HashMap<String,Object>>(new HashMap<String, Object>() ,HttpStatus.OK));
		ResponseEntity<HashMap<String,Object>> result =  weatherController.getAverageMetricsForSensors(sensorIds, "week");
		assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
		
	}
	
	@Test
	public void getAverageMetricsForSensorsBadRequestTest() {
		when(weatherService.getAverageMetricsForSensors(new ArrayList<Integer>(), "")).thenReturn(new ResponseEntity<HashMap<String,Object>>(new HashMap<String, Object>() ,HttpStatus.BAD_REQUEST));
		ResponseEntity<HashMap<String,Object>> result =  weatherController.getAverageMetricsForSensors(new ArrayList<Integer>(), "");
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
		
		when(weatherService.getAverageMetricsForSensors(new ArrayList<Integer>(Arrays.asList(1,2)), null)).thenReturn(new ResponseEntity<HashMap<String,Object>>(new HashMap<String, Object>() ,HttpStatus.BAD_REQUEST));
		result =  weatherController.getAverageMetricsForSensors(new ArrayList<Integer>(Arrays.asList(3,4)), null);
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
		
		when(weatherService.getAverageMetricsForSensors(null, "week")).thenReturn(new ResponseEntity<HashMap<String,Object>>(new HashMap<String, Object>() ,HttpStatus.BAD_REQUEST));
		result =  weatherController.getAverageMetricsForSensors(null, "week");
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
		
		when(weatherService.getAverageMetricsForSensors(null, "abcde")).thenReturn(new ResponseEntity<HashMap<String,Object>>(new HashMap<String, Object>() ,HttpStatus.BAD_REQUEST));
		result =  weatherController.getAverageMetricsForSensors(null, "abcd");
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
		
	}
	
	
		
	
	
}
