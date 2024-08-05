public class WeatherDataAPI {
    private WeatherData weatherData;

    public WeatherDataAPI(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public void fetchDataFromAPI() {
        float temperature = 27;
        float humidity =68;
        float pressure = 145;

        weatherData.setMeasurements(temperature, humidity, pressure);
    }
}

