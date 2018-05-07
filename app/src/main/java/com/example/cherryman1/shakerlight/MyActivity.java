package com.example.cherryman1.shakerlight;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.ImageView;

public class MyActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private ShakeDetector shakeDetector;
    private ImageView lightOn;
    private ImageView lightOff;
    private LayoutInflater layoutInflater;
    private boolean lightIsOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        lightOff = (ImageView) layoutInflater.inflate(R.layout.light_off, null);
        lightIsOn = false;
        lightOn = (ImageView) findViewById(R.id.lightOn);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        shakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                flipLight();
            }
        });
    }

    private void flipLight()
    {
        if (!lightIsOn)
        {
            layoutInflater.inflate(R.layout.light_on, null);
        }
        else
        {
            layoutInflater.inflate(R.layout.light_off, null);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(shakeDetector, sensorAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause()
    {
        sensorManager.unregisterListener(shakeDetector, sensorAccelerometer);
        super.onPause();
    }
}
