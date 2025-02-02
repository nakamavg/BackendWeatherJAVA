package tech.davidgomezmartin.Weather.model;

public class WeatherResponse {

    private String city;
    private double temperature;
    private String description;

    // Constructor vacío
    public WeatherResponse() {
    }

    // Constructor con parámetros
    public WeatherResponse(String city, double temperature, String description) {
        this.city = city;
        this.temperature = temperature;
        this.description = description;
    }

    // Getters y Setters
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}