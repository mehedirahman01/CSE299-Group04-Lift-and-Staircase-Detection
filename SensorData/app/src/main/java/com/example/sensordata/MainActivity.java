package com.example.sensordata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";
    private SensorManager sensorManager;
    Sensor accelerometer,mGyro;

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



        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);

       // accelerometer= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //sensorManager.registerListener(MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_GAME);

       // mGyro= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
       // sensorManager.registerListener(MainActivity.this,mGyro,SensorManager.SENSOR_DELAY_GAME);
        Log.d(TAG, "onCreate: Registered Accelerometer");

    }

    @Override
    protected void onStart() {
        super.onStart();

        accelerometer= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_GAME);

        mGyro= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(MainActivity.this,mGyro,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor=sensorEvent.sensor;

        if(sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            Log.d(TAG, "onSensorChanged: X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z: " + sensorEvent.values[2]);
            xValue.setText("xValue: " + String.format("%.3f",sensorEvent.values[0]));
            yValue.setText("yValue: " + String.format("%.3f",sensorEvent.values[1]));
            zValue.setText("zValue: " + String.format("%.3f",sensorEvent.values[2]));
        } else if(sensor.getType()==Sensor.TYPE_GYROSCOPE) {
            xGyroValue.setText("xGValue: " + String.format("%.3f",sensorEvent.values[0]));
            yGyroValue.setText("yGValue: " + String.format("%.3f",sensorEvent.values[1]));
            zGyroValue.setText("zGValue: " + String.format("%.3f",sensorEvent.values[2]));
        }

    }
}
