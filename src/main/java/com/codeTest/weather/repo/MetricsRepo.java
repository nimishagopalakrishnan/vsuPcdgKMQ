package com.codeTest.weather.repo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codeTest.weather.entity.Metrics;
import com.codeTest.weather.model.AverageMetricsResponse;

public interface MetricsRepo extends JpaRepository<Metrics, Integer>{
	
	@Query("SELECT s.sensorId AS sensorId, AVG(m.temperature) AS tempAverage, AVG(m.precipitation) AS precipAverage, AVG(m.windSpeed) AS windspeedAverage FROM metrics m JOIN m.sensor s WHERE s.sensorId IN :sensorIds AND m.date BETWEEN :from AND :to GROUP BY s")
	List<AverageMetricsResponse> findAverageMetricsForSensors(@Param("sensorIds") ArrayList<Integer> sensorIds, @Param("from") Date from, @Param("to") Date to);

}
