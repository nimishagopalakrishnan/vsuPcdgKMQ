# vsuPcdgKMQ
Repo named after random string

1. First execute db queries present in src/main/resources/DB_queries.sql
    I used MySQL v8.0.23 for this
2. Update db details like url, username and password in src/main/resources/application.properties
3. Execute goals clean install and run as java application.
4. Jacoco code coverage report generated after tests executed is available at target/site/jacoco/index.html 
5. Sample of Postman requests for 
    - POST register sensor endpoint  : registering one sensor object per api call
    	localhost:8992/registerSensor
       - Request Body: 
       
       ```
       {"sensorId":1,"countryName":"ITALY","cityName":"ROME"}
       ```
       
    - POST add metrics endpoint : registering one metric object per api call
      localhost:8992/addSensorMetrics
        - Request Body :
        
       ```
         {
    		"sensor": {"sensorId": 1},
    		"date": "2022-04-24T14:20:12",
    		"temperature": 11,
    		"precipitation": 8.0,
    		"windSpeed": 20.0
			}
		```
	
	- GET get average metrics endpoint : query can accept multiple sensor Ids and take dateRange values as 'week', 'month' and if not present executes average for current day by default.
		- Response gives average of all metrics for the specified dateRange for required sensors whose metrics are available.
		  -  localhost:8992/getAverageMetricsForSensor?sensorIds=1&sensorIds=2&dateRange=week
			- Sample response

			```
			{
			    "result": [
				{
				    "tempAverage": 21.76923,
				    "precipAverage": 11.307693,
				    "sensorId": 1,
				    "windspeedAverage": 17.307692
				},
				{
				    "tempAverage": 18.0,
				    "precipAverage": 3.0,
				    "sensorId": 2,
				    "windspeedAverage": 0.0
				}
			    ],
			    "message": "Average metrics for sensors for date range: week"
			}
			```

		  - localhost:8992/getAverageMetricsForSensor?sensorIds=1&sensorIds=2&sensorIds=3&dateRange=month
		  - localhost:8992/getAverageMetricsForSensor?sensorIds=1            //(default:today)

		


	
     
