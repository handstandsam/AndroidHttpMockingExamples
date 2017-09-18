package com.joshskeen.weatherview.service;

import com.joshskeen.weatherview.inject.InjectionFactory;
import com.joshskeen.weatherview.model.ConditionsServiceResponse;

import retrofit2.Call;

public class WeatherServiceManager {

    private final WeatherServiceInterface mWeatherServiceInterface;

    public WeatherServiceManager(String weatherServiceEndpoint) {
        mWeatherServiceInterface = InjectionFactory.buildRetrofit(weatherServiceEndpoint)
                .create(WeatherServiceInterface.class);
    }

    public Call<ConditionsServiceResponse> getConditionsFor(String name) {
        return mWeatherServiceInterface.getConditions(name);
    }
}
