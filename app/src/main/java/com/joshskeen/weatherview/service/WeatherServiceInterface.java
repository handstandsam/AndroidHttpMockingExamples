package com.joshskeen.weatherview.service;


import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.model.ConditionsServiceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface WeatherServiceInterface {

    @GET("/api/" + BuildConfig.WEATHERVIEW_API_KEY + "/conditions/q/CA/{location}.json")
    public Call<ConditionsServiceResponse> getConditions(@Path("location") String location);

}
