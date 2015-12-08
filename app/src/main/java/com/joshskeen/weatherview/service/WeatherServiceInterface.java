package com.joshskeen.weatherview.service;


import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.model.ConditionsServiceResponse;

import retrofit.http.GET;
import retrofit.http.Path;


public interface WeatherServiceInterface {

    @GET("/api/" + BuildConfig.WEATHERVIEW_API_KEY + "/conditions/q/CA/{location}.json")
    public ConditionsServiceResponse getConditions(@Path("location") String location);

}
