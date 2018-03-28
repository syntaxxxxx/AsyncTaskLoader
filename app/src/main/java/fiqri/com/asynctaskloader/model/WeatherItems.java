package fiqri.com.asynctaskloader.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherItems {

    private int id;
    private String nama, currentWeather, desk, temp;

    public WeatherItems(JSONObject object) {

        try {
            int id = object.getInt("id");
            String nama = object.getString("name");
            String currentWeather = object.getJSONArray("weather").getJSONObject(0)
                    .getString("main");
            String description = object.getJSONArray("weather").getJSONObject(0)
                    .getString("description");

            double tempInKelvin = object.getJSONObject("main").getDouble("temp");
            double tempInCelcius = tempInKelvin - 273;

            String temperature = new DecimalFormat("##.##").format(tempInCelcius);

            this.id = id;
            this.nama = nama;
            this.currentWeather = currentWeather;
            this.desk = description;
            this.temp = temperature;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(String currentWeather) {
        this.currentWeather = currentWeather;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
