package tech.davidgomezmartin.Weather.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.davidgomezmartin.Weather.WeatherService;
import tech.davidgomezmartin.Weather.model.WeatherResponse;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public WeatherResponse getWeather(@PathVariable String city) {
        return weatherService.getWeatherData(city);
    }

}

@RestController
@RequestMapping("/")
class HomeController {
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("<h1>Api Index</h1> " +
                "<h2>BIENVENIDO  a la api de david</h2>");
    }
}
