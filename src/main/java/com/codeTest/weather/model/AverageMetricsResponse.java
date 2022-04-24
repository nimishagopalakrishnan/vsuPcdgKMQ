package com.codeTest.weather.model;

public interface AverageMetricsResponse {
	Integer getSensorId();
	Float getTempAverage();
	Float getPrecipAverage();
	Float getWindspeedAverage();
}
