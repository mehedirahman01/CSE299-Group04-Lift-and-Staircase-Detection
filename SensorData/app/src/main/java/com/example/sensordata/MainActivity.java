package com.example.sensordata;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Button buttonStart;
    Button buttonStop;
    boolean isRunning;
    final String TAG = "SensorLogger";
    FileWriter writer;
    TextView xValue,yValue,zValue,xGyroValue,yGyroValue,zGyroValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xValue= (TextView) findViewById(R.id.xValue);
        yValue=(TextView) findViewById(R.id.yValue);
        zValue=(TextView) findViewById(R.id.zValue);

        xGyroValue=(TextView) findViewById(R.id.xGyroValue);
        yGyroValue=(TextView) findViewById(R.id.yGyroValue);
        zGyroValue=(TextView) findViewById(R.id.zGyroValue);



        isRunning = false;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        buttonStart = (Button)findViewById(R.id.buttonStart);
        buttonStop = (Button)findViewById(R.id.buttonStop);

        buttonStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);

                Log.d(TAG, "Writing to " + getStorageDir());
                try {
                    writer = new FileWriter(new File(getStorageDir(), "sensors_" + System.currentTimeMillis() + ".csv"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                sensorManager.registerListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
                sensorManager.registerListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);

                isRunning = true;
                return true;
            }
        });
        buttonStop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                buttonStart.setEnabled(true);
                buttonStop.setEnabled(false);
                isRunning = false;
                sensorManager.flush(MainActivity.this);
                sensorManager.unregisterListener(MainActivity.this);
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    private String getStorageDir() {
        return "/storage/emulated/0/SensorLogger/";
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(isRunning) {
            try {
                switch(sensorEvent.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        xValue.setText("xValue: " + String.format("%.3f",sensorEvent.values[0]));
                        yValue.setText("yValue: " + String.format("%.3f",sensorEvent.values[1]));
                        zValue.setText("zValue: " + String.format("%.3f",sensorEvent.values[2]));
                        writer.write(String.format("%d; ACC; %f; %f; %f; %f; %f; %f\n", sensorEvent.timestamp, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2], 0.f, 0.f, 0.f));
                        break;
                    case Sensor.TYPE_GYROSCOPE:
                        xGyroValue.setText("xGValue: " + String.format("%.3f",sensorEvent.values[0]));
                        yGyroValue.setText("yGValue: " + String.format("%.3f",sensorEvent.values[1]));
                        zGyroValue.setText("zGValue: " + String.format("%.3f",sensorEvent.values[2]));
                        writer.write(String.format("%d; GYRO; %f; %f; %f; %f; %f; %f\n", sensorEvent.timestamp, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2], 0.f, 0.f, 0.f));
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
