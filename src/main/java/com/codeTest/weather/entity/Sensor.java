package com.codeTest.weather.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sensor")
public class Sensor {
	@Id
	@Column(name="sensor_id",nullable=false)
	private int sensorId;
	
	@Column(name="country_name")
	private String countryName;
	
	@Column(name="city_name")
	private String cityName;
	
	@OneToMany(mappedBy="sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Metrics> metrics = new HashSet<>();

	public int getSensorId() {
		return sensorId;
	}

	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Set<Metrics> getMetrics() {
		return metrics;
	}

	public void setMetrics(Set<Metrics> metrics) {
		this.metrics = metrics;
	}

	@Override
	public String toString() {
		return "Sensor [sensorId=" + sensorId + ", countryName=" + countryName + ", cityName=" + cityName + ", metrics="
				+ metrics + "]";
	}

	public Sensor(int sensorId, String countryName, String cityName, Set<Metrics> metrics) {
		super();
		this.sensorId = sensorId;
		this.countryName = countryName;
		this.cityName = cityName;
		this.metrics = metrics;
	}

	public Sensor() {
		super();
	}
}