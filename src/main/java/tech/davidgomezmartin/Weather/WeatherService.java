package tech.davidgomezmartin.Weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.davidgomezmartin.Weather.model.WeatherResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final String apiKey;

    @Autowired
    public WeatherService(RestTemplateBuilder restTemplateBuilder, Environment environment) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = environment.getProperty("OPENWEATHER_API_KEY");
    }

    public WeatherResponse getWeatherData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("name")) {
                throw new RuntimeException("No se encontró información para la ciudad: " + city);
            }

            WeatherResponse weatherResponse = new WeatherResponse();
            Map<String, Object> mainData = (Map<String, Object>) response.get("main");
            Map<String, Object> windData = (Map<String, Object>) response.get("wind");
            Map<String, Object> cloudsData = (Map<String, Object>) response.get("clouds");
            Map<String, Object> sysData = (Map<String, Object>) response.get("sys");

            if (mainData == null || windData == null || cloudsData == null || sysData == null) {
                throw new RuntimeException("Datos meteorológicos incompletos para la ciudad: " + city);
            }

            List<Map<String, String>> weatherList = (List<Map<String, String>>) response.get("weather");
            if (weatherList == null || weatherList.isEmpty()) {
                throw new RuntimeException("Descripción del clima no disponible para la ciudad: " + city);
            }

            weatherResponse.setCity((String) response.get("name"));
            weatherResponse.setTemperature(mainData.get("temp") != null ? Double.parseDouble(mainData.get("temp").toString()) : 0.0);
            weatherResponse.setDescription(weatherList.get(0).get("description"));
            weatherResponse.setFeelsLike(mainData.get("feels_like") != null ? Double.parseDouble(mainData.get("feels_like").toString()) : 0.0);
            weatherResponse.setHumidity(mainData.get("humidity") != null ? Integer.parseInt(mainData.get("humidity").toString()) : 0);
            weatherResponse.setPressure(mainData.get("pressure") != null ? Integer.parseInt(mainData.get("pressure").toString()) : 0);
            weatherResponse.setWindSpeed(windData.get("speed") != null ? Double.parseDouble(windData.get("speed").toString()) : 0.0);
            weatherResponse.setWindDegree(windData.get("deg") != null ? Integer.parseInt(windData.get("deg").toString()) : 0);
            weatherResponse.setCloudiness(cloudsData.get("all") != null ? Integer.parseInt(cloudsData.get("all").toString()) : 0);
            weatherResponse.setVisibility(response.get("visibility") != null ? Integer.parseInt(response.get("visibility").toString()) : 0);
            
            // Convert Unix timestamps to readable time strings
            long sunriseTime = Long.parseLong(sysData.get("sunrise").toString()) * 1000;
            long sunsetTime = Long.parseLong(sysData.get("sunset").toString()) * 1000;
            weatherResponse.setSunrise(new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(sunriseTime)));
            weatherResponse.setSunset(new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(sunsetTime)));
            weatherResponse.setCountry((String) sysData.get("country"));
            weatherResponse.setLocalTime(new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date()));
            
            // Convertir timestamp a hora local
            long timestamp = Long.parseLong(response.get("dt").toString()) * 1000;
            String localTime = Instant.ofEpochMilli(timestamp)
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            weatherResponse.setLocalTime(localTime);

            return weatherResponse;

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener datos meteorológicos para la ciudad: " + city, e);
        }
    }
}