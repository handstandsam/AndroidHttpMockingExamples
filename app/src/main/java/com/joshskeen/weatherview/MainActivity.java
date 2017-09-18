package com.joshskeen.weatherview;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.joshskeen.weatherview.model.ConditionsServiceResponse;
import com.joshskeen.weatherview.model.WeatherCondition;
import com.joshskeen.weatherview.service.WeatherServiceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends Activity implements View.OnClickListener {
    protected EditText text;
    protected Button button;

    @Override
    public void onClick(View v) {
        text.setText("");
    }

    WeatherServiceManager weatherServiceManager = new WeatherServiceManager("http://api.wunderground.com/");

    public void setWeatherServiceManager(WeatherServiceManager mWeatherServiceManager) {
        this.weatherServiceManager = mWeatherServiceManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        text = (EditText) findViewById(R.id.editText);
        text.setOnClickListener(this);

        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        String location = text.getText().toString();
                        weatherServiceManager.getConditionsFor(location).enqueue(new Callback<ConditionsServiceResponse>() {
                            @Override
                            public void onResponse(Call<ConditionsServiceResponse> call, Response<ConditionsServiceResponse> response) {
                                List<WeatherCondition> conditionsForCity = response.body().getConditionsResponse().getWeatherConditions();
                                if (conditionsForCity != null) {
                                    StringBuffer out = new StringBuffer();
                                    for (WeatherCondition wc : conditionsForCity) {
                                        out.append(wc.toString());
                                    }

                                    TextView outText = (TextView) findViewById(R.id.textView);
                                    outText.setText(out.toString());
                                }
                            }

                            @Override
                            public void onFailure(Call<ConditionsServiceResponse> call, Throwable t) {

                            }
                        });


                    }
                });
    }
}
