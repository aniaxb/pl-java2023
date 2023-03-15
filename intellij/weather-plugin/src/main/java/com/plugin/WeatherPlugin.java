package com.plugin;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WeatherPlugin extends JFrame {
    private static final String API_URL = "https://api.open-meteo.com/v1/forecast";
    private static final double LATITUDE = 52.23;
    private static final double LONGITUDE = 21.02;

    public WeatherPlugin(){
        super("Weather Plugin");

        JPanel contentPane = new JPanel(new FlowLayout());
        setContentPane(contentPane);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(API_URL + "?latitude=" + LATITUDE +
                "&longitude=" + LONGITUDE +
                "&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m");
        try {
            HttpResponse response = httpClient.execute(request);

            String responseContent = EntityUtils.toString(response.getEntity());

            JSONObject jsonResponse = new JSONObject(responseContent);
            JSONObject currentWeather = jsonResponse.getJSONObject("current_weather");
            String time = currentWeather.getString("time");
            double temperature = currentWeather.getDouble("temperature");
            int weatherCode = currentWeather.getInt("weathercode");
            double windSpeed = currentWeather.getDouble("windspeed");
            double windDirection = currentWeather.getDouble("winddirection");

            JLabel title = new JLabel("Weather forecast");
            JLabel label = new JLabel("Time: " + time + ", Temperature: " + temperature +
                    ", Weather Code: " + weatherCode + ", Wind Speed: " + windSpeed +
                    ", Wind Direction: " + windDirection);

            label.setForeground(Color.BLACK);
            contentPane.add(title);
            contentPane.add(label);

            ImageIcon backgroundImage = new ImageIcon("src/main/resources/bradpitt.gif");
            JLabel backgroundLabel = new JLabel(backgroundImage);

            contentPane.add(backgroundLabel);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        pack();
        setSize(625, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new WeatherPlugin();
    }
}