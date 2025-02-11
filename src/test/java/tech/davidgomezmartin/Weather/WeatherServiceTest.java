package tech.davidgomezmartin.Weather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import tech.davidgomezmartin.Weather.model.WeatherResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Environment environment;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        when(environment.getProperty("OPENWEATHER_API_KEY")).thenReturn("test-api-key");
        weatherService = new WeatherService(restTemplateBuilder, environment);
    }

    @Test
    void getWeatherData_SuccessfulResponse() {
        // Prepare mock response
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("name", "Madrid");

        Map<String, Object> main = new HashMap<>();
        main.put("temp", 20.5);
        main.put("feels_like", 19.8);
        main.put("humidity", 65);
        main.put("pressure", 1013);
        mockResponse.put("main", main);

        Map<String, Object> wind = new HashMap<>();
        wind.put("speed", 3.6);
        wind.put("deg", 280);
        mockResponse.put("wind", wind);

        Map<String, Object> clouds = new HashMap<>();
        clouds.put("all", 75);
        mockResponse.put("clouds", clouds);

        Map<String, Object> sys = new HashMap<>();
        sys.put("country", "ES");
        sys.put("sunrise", 1621138800L);
        sys.put("sunset", 1621190400L);
        mockResponse.put("sys", sys);

        mockResponse.put("visibility", 10000);
        mockResponse.put("dt", 1621162800L);

        Map<String, String> weatherDesc = new HashMap<>();
        weatherDesc.put("description", "partly cloudy");
        mockResponse.put("weather", List.of(weatherDesc));

        String expectedUrl = "https://api.openweathermap.org/data/2.5/weather?q=Madrid&appid=test-api-key&units=metric";
        when(restTemplate.getForObject(eq(expectedUrl), eq(Map.class))).thenReturn(mockResponse);

        // Test
        WeatherResponse response = weatherService.getWeatherData("Madrid");

        // Verify
        assertNotNull(response);
        assertEquals("Madrid", response.getCity());
        assertEquals(20.5, response.getTemperature());
        assertEquals("partly cloudy", response.getDescription());
        assertEquals(19.8, response.getFeelsLike());
        assertEquals(65, response.getHumidity());
        assertEquals(1013, response.getPressure());
        assertEquals(3.6, response.getWindSpeed());
        assertEquals(280, response.getWindDegree());
        assertEquals(75, response.getCloudiness());
        assertEquals(10000, response.getVisibility());
        assertEquals("ES", response.getCountry());
        assertNotNull(response.getSunrise());
        assertNotNull(response.getSunset());
        assertNotNull(response.getLocalTime());
    }

    @Test
    void getWeatherData_CityNotFound() {
        String nonExistentCity = "NonExistentCity";
        String expectedUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + nonExistentCity + "&appid=test-api-key&units=metric";
        when(restTemplate.getForObject(eq(expectedUrl), eq(Map.class))).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> 
            weatherService.getWeatherData(nonExistentCity)
        );

        assertTrue(exception.getMessage().contains("No se encontró información para la ciudad"));
    }

    @Test
    void getWeatherData_ApiError() {
        String city = "Madrid";
        String expectedUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=test-api-key&units=metric";
        when(restTemplate.getForObject(eq(expectedUrl), eq(Map.class))).thenThrow(new RuntimeException("API Error"));

        Exception exception = assertThrows(RuntimeException.class, () -> 
            weatherService.getWeatherData(city)
        );

        assertTrue(exception.getMessage().contains("Error al obtener datos meteorológicos"));
    }
}