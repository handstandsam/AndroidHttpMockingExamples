package com.joshskeen.weatherview.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joshskeen.weatherview.inject.InjectionFactory;
import com.joshskeen.weatherview.model.WeatherCondition;

import java.io.IOException;
import java.util.List;

public class WeatherServiceManager {

    private static Gson gson = new GsonBuilder().create();

    private final WeatherServiceInterface mWeatherServiceInterface;

    public WeatherServiceManager(String weatherServiceEndpoint) {
        mWeatherServiceInterface = InjectionFactory.buildRetrofit(weatherServiceEndpoint)
                .create(WeatherServiceInterface.class);
    }

    public List<WeatherCondition> getConditionsFor(String name) {
        try {
            return mWeatherServiceInterface.getConditions(name).execute().body().getConditionsResponse()
                    .getWeatherConditions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
