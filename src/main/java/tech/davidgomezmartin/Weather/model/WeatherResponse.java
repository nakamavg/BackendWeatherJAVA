package tech.davidgomezmartin.Weather.model;

public class WeatherResponse {

    private String city;
    private double temperature;
    private String description;
    private String country;
    private String localTime;
    private double feelsLike;
    private int humidity;
    private int pressure;
    private double windSpeed;
    private int windDegree;
    private int cloudiness;
    private int visibility;
    private String sunrise;
    private String sunset;

    // Constructor vacío
    public WeatherResponse() {
    }

    // Constructor con parámetros
    public WeatherResponse(String city, double temperature, String description, String country, String localTime,
                         double feelsLike, int humidity, int pressure, double windSpeed, int windDegree,
                         int cloudiness, int visibility, String sunrise, String sunset) {
        this.city = city;
        this.temperature = temperature;
        this.description = description;
        this.country = country;
        this.localTime = localTime;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.cloudiness = cloudiness;
        this.visibility = visibility;
        this.sunrise = sunrise;
        this.sunset = sunset;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(int windDegree) {
        this.windDegree = windDegree;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}