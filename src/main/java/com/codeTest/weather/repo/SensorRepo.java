package com.codeTest.weather.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeTest.weather.entity.Sensor;

public interface SensorRepo extends JpaRepository<Sensor, Integer>{

	
}
