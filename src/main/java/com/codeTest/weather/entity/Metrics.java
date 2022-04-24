package com.codeTest.weather.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity (name="metrics")
@Table(name = "metrics")
public class Metrics {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "metric_id")
	private int metricId;
	
	@ManyToOne
    @JoinColumn(name = "sensor_id")
	private Sensor sensor;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="temperature")
	private float temperature;
	
	@Column(name="precipitation")
	private float precipitation;
	
	@Column(name="wind_speed")
	private float windSpeed;

	public int getMetricId() {
		return metricId;
	}

	public void setMetricId(int metricId) {
		this.metricId = metricId;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(float precipitation) {
		this.precipitation = precipitation;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	@Override
	public String toString() {
		return "Metrics [metricId=" + metricId + ", sensor=" + sensor + ", date=" + date + ", temperature="
				+ temperature + ", precipitation=" + precipitation + ", windSpeed=" + windSpeed + "]";
	}

	public Metrics(int metricId, Sensor sensor, Date date, float temperature, float precipitation, float windSpeed) {
		super();
		this.metricId = metricId;
		this.sensor = sensor;
		this.date = date;
		this.temperature = temperature;
		this.precipitation = precipitation;
		this.windSpeed = windSpeed;
	}

	public Metrics() {
		super();

	}
}