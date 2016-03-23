package com.raenarapps.simplecollage;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {
    private static final float SHAKE_THRESHOLD_GRAVITY = 1.5F;
    private static final int SHAKE_INTERVAL_TIME_MS = 1000;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private long shakeTimestamp;
    private int shakeCount;

    private OnShakeListener listener;

    public interface OnShakeListener {
        void onShake(int count);
    }

    public ShakeDetector(OnShakeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        // gForce will be close to 1 when there is no movement.
        float gForce = (float) Math.sqrt((double)(gX * gX + gY * gY + gZ * gZ));

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            final long now = System.currentTimeMillis();

            if (shakeTimestamp + SHAKE_INTERVAL_TIME_MS > now) {
                return;
            }

            if (shakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                shakeCount = 0;
            }

            shakeTimestamp = now;
            shakeCount++;

            if (shakeCount == 5){
                listener.onShake(shakeCount);
                shakeCount = 0;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
