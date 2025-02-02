package tech.davidgomezmartin.Weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.davidgomezmartin.Weather.model.WeatherResponse;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final String apiKey;

    @Autowired
    public WeatherService(RestTemplateBuilder restTemplateBuilder, Environment environment) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = environment.getProperty("OPENWEATHER_API_KEY"); // Lee la clave desde una variable de entorno
    }

    public WeatherResponse getWeatherData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("name")) {
                throw new RuntimeException("No se encontró información para la ciudad: " + city);
            }

            WeatherResponse weatherResponse = new WeatherResponse();
            weatherResponse.setCity((String) response.get("name"));
            weatherResponse.setTemperature(Double.parseDouble(((Map<String, Object>) response.get("main")).get("temp").toString()));
            weatherResponse.setDescription(((List<Map<String, String>>) response.get("weather")).get(0).get("description"));

            return weatherResponse;

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener datos meteorológicos para la ciudad: " + city, e);
        }
    }
}