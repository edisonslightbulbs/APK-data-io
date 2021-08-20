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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView m_accelerometerX;
    TextView m_accelerometerY;
    TextView m_accelerometerZ;

    private SensorManager m_sensorManager;
    private Sensor m_accelerometer;

    // file io
    File m_contextPath;
    String m_filePath;
    FileWriter m_writer;
    FileOutputStream m_outputStream;

    // round up to n number of decimal places
    DecimalFormat df = new DecimalFormat("#.######");

    private static final String TAG = "MAIN_ACTIVITY";
    private static final String DIRECTORY = "accelerometer";
    private static final String FILE_NAME = "data.csv";

    void contextPath(){
        m_contextPath = new File(this.getFilesDir(), DIRECTORY);
        if(!m_contextPath.exists()){
            m_contextPath.mkdir();
        }
        m_filePath = m_contextPath + "/" + FILE_NAME;
    }

    void writeFile(String str){
        try {
            m_outputStream = new FileOutputStream(m_filePath, true);
            m_writer = new FileWriter(m_outputStream.getFD());
            m_writer.write(str);
            m_writer.close();
            m_outputStream.getFD().sync();
            m_outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // create file | ensure it exists
        contextPath();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        df.setRoundingMode(RoundingMode.CEILING);

        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (m_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            m_accelerometer = m_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Utils.toast(this, "accelerometer detected");
        } else {
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

        // write to file
        String data = xValue + ", " + yValue + ", " + zValue;
        writeFile(data + "\n");
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