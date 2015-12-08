package com.joshskeen.weatherview;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.joshskeen.weatherview.model.WeatherCondition;
import com.joshskeen.weatherview.service.WeatherServiceManager;

import java.util.List;


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
                        List<WeatherCondition> conditionsForCity = weatherServiceManager.getConditionsFor(text.getText().toString());
                        if (conditionsForCity != null) {
                            StringBuffer out = new StringBuffer();
                            for (WeatherCondition wc : conditionsForCity) {
                                out.append(wc.toString());
                            }

                            TextView outText = (TextView) findViewById(R.id.textView);
                            outText.setText(out.toString());
                        }
                    }
                });
    }
}
