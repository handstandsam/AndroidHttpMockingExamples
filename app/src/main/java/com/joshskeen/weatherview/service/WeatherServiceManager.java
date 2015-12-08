package com.joshskeen.weatherview.service;

import com.joshskeen.weatherview.model.WeatherCondition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import retrofit.RestAdapter;

public class WeatherServiceManager {
    Logger logger = LoggerFactory.getLogger(WeatherServiceManager.class);

    private String mWeatherServiceEndpoint;
    private final WeatherServiceInterface mWeatherServiceInterface;

    public WeatherServiceManager(String weatherServiceEndpoint) {
        mWeatherServiceEndpoint = weatherServiceEndpoint;
        mWeatherServiceInterface = buildRestAdapter()
                .create(WeatherServiceInterface.class);
    }

    public List<WeatherCondition> getConditionsFor(String name) {
        return mWeatherServiceInterface.getConditions(name)
                .getConditionsResponse()
                .getWeatherConditions();
    }

    private RestAdapter buildRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint(mWeatherServiceEndpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        System.out.println("==>" + message);
                    }
                })
                .build();
    }
}
