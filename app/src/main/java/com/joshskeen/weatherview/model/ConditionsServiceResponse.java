package com.joshskeen.weatherview.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConditionsServiceResponse {

    public class ConditionsResponse {
        @SerializedName("results")
        public List<WeatherCondition> mWeatherConditions;

        public List<WeatherCondition> getWeatherConditions() {
            return mWeatherConditions;
        }

        @Override
        public String toString() {
            return "ConditionsResponse{" +
                    "mWeatherConditions=" + mWeatherConditions +
                    '}';
        }
    }

    @SerializedName("response")
    public ConditionsResponse mConditionsResponse;

    public ConditionsResponse getConditionsResponse() {
        return mConditionsResponse;
    }

    @Override
    public String toString() {
        return "ConditionsServiceResponse{" +
                "mConditionsResponse=" + mConditionsResponse +
                '}';
    }
}
