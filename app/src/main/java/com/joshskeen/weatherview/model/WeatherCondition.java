package com.joshskeen.weatherview.model;

import com.google.gson.annotations.SerializedName;

public class WeatherCondition {

    @SerializedName("name")
    public String mName;

    @SerializedName("city")
    public String mCity;

    @SerializedName("state")
    public String mState;

    @SerializedName("country")
    public String mCountry;

    @Override
    public String toString() {
        return "WeatherCondition{" +
                "mName='" + mName + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mState='" + mState + '\'' +
                ", mCountry='" + mCountry + '\'' +
                '}';
    }
}
