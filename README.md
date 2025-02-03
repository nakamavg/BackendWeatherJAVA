# BackendWeatherJAVA
## Enlace al back
### [Endpoint a Madrid](https://backendweatherjava-production.up.railway.app/api/weather/madrid)

## Little backend with these endpoints

### @RequestMapping("/api/weather")

#### This class will operate starting from this route:

### @GetMapping("/{city}")

```url
    /api/weather/Malaga
```

### This will display the weather in Malaga.

}

### The WeatherService class

#### Handles the connection with the weather API.

- The API key is retrieved from the server environment variables where the application is running. This ensures that
  credentials are not stored in the codebase or uploaded to version control systems like GitHub, preventing unauthorized
  access.

```java
Map<String, Object> response = restTemplate.getForObject(url, Map.class);
```

The fetched data is stored in a map.