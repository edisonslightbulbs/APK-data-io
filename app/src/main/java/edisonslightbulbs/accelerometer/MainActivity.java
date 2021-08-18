package edisonslightbulbs.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView m_accelerometerX;
    TextView m_accelerometerY;
    TextView m_accelerometerZ;

    private SensorManager m_sensorManager;
    private Sensor m_accelerometer;

    // round up to n number of decimal places
    DecimalFormat df = new DecimalFormat("#.######");

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        df.setRoundingMode(RoundingMode.CEILING);

        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (m_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            m_accelerometer = m_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Utils.toast(this, "accelerometer detected");
        } else{
            Utils.toast(this, "sorry, accelerometer not detected");
            Log.e(TAG, "-- accelerometer sensor not found!");
        }

        m_accelerometerX = findViewById(R.id.accXTextView);
        m_accelerometerY = findViewById(R.id.accYTextView);
        m_accelerometerZ = findViewById(R.id.accZTextView);

        m_accelerometerX.setText("0.0");
        m_accelerometerY.setText("0.0");
        m_accelerometerZ.setText("0.0");
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        String xValue = df.format(event.values[0]);
        String yValue = df.format(event.values[1]);
        String zValue = df.format(event.values[2]);

        m_accelerometerX.setText(xValue);
        m_accelerometerY.setText(yValue);
        m_accelerometerZ.setText(zValue);
    }

    @Override
    protected void onResume() {
        super.onResume();
        m_sensorManager.registerListener(this, m_accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        m_sensorManager.unregisterListener(this);
    }
}