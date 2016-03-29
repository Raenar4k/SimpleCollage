package com.raenarapps.simplecollage.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.raenarapps.simplecollage.R;
import com.raenarapps.simplecollage.util.ShakeDetector;
import com.raenarapps.simplecollage.util.ShareTask;
import com.raenarapps.simplecollage.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class CollageFragment extends Fragment {
    private static final String TAG = CollageFragment.class.getSimpleName();
    public static final String SHUFFLE_LIST = "SHUFFLE_LIST";
    public static final String USED_COMBINATIONS = "USED_COMBINATIONS";
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private HashSet<Integer> usedCombinations;
    private ArrayList<String> shuffleList;
    private long combinationsCount;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private ShakeDetector shakeDetector;
    private AlertDialog progressDialog;
    private AlertDialog shakeDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_collage, container, false);
        imageView1 = (ImageView) rootView.findViewById(R.id.imageView1);
        imageView2 = (ImageView) rootView.findViewById(R.id.imageView2);
        imageView3 = (ImageView) rootView.findViewById(R.id.imageView3);
        imageView4 = (ImageView) rootView.findViewById(R.id.imageView4);

        if (savedInstanceState != null) {
            shuffleList = savedInstanceState.getStringArrayList(SHUFFLE_LIST);
            usedCombinations = (HashSet<Integer>) savedInstanceState.getSerializable(USED_COMBINATIONS);
        } else {
            String[] urlArray = getArguments().getStringArray(Utility.SELECTED_URLS_ARRAY);
            shuffleList = new ArrayList<>(Arrays.asList(urlArray));
            usedCombinations = new HashSet<>();
            List<String> shortList = shuffleList.subList(0, Utility.COLLAGE_IMAGES_COUNT);
            usedCombinations.add(shortList.hashCode());
        }
        combinationsCount = getCombinationsCount(shuffleList.size());

        loadImages(shuffleList);
        Button shareButton = (Button) rootView.findViewById(R.id.buttonShare);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {
                    shareCollage(rootView);
                }
            }
        });
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        shakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                shufflePictures();
                v.vibrate(200);
            }
        });
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        progressDialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_progress).create();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (prefs.getBoolean(Utility.FIRST_LAUNCH_KEY, true)) {
            shakeDialog = new AlertDialog.Builder(getContext())
                    .setMessage(getContext().getString(R.string.dialog_shake))
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setView(R.layout.dialog_shake).create();
            shakeDialog.show();
            prefs.edit().putBoolean(Utility.FIRST_LAUNCH_KEY, false).apply();
        }
        return rootView;
    }

    private void loadImages(ArrayList<String> urlList) {
        Picasso.with(getContext()).load(urlList.get(0))
                .fit().centerCrop()
                .into(imageView1);
        Picasso.with(getContext()).load(urlList.get(1))
                .fit().centerCrop()
                .into(imageView2);
        Picasso.with(getContext()).load(urlList.get(2))
                .fit().centerCrop()
                .into(imageView3);
        Picasso.with(getContext()).load(urlList.get(3))
                .fit().centerCrop()
                .into(imageView4);
    }

    private void shareCollage(View rootView) {
        View collageView = rootView.findViewById(R.id.layoutCollage);
        collageView.setDrawingCacheEnabled(true);
        collageView.buildDrawingCache();
        Bitmap drawingCache = collageView.getDrawingCache();
        progressDialog.show();
        new ShareTask(getContext()).execute(drawingCache);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, sensorAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeDetector);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (shakeDialog != null && shakeDialog.isShowing()) {
            shakeDialog.dismiss();
        }
    }

    private void shufflePictures() {
        if (usedCombinations.size() < combinationsCount) {
            boolean combinationFound = false;
            do {
                Collections.shuffle(shuffleList);
                List<String> shortList = shuffleList.subList(0, Utility.COLLAGE_IMAGES_COUNT);
                int hashCodeNew = shortList.hashCode();
                if (!usedCombinations.contains(hashCodeNew)) {
                    usedCombinations.add(hashCodeNew);
                    loadImages(shuffleList);
                    combinationFound = true;
                }
            } while (!combinationFound);
        } else {
            Toast.makeText(getContext(), R.string.collage_combinations, Toast.LENGTH_SHORT).show();
        }
    }

    private long getCombinationsCount(int size) {
        //count = n!/(n-k)!
        // n - number of elements (selected images count), k - arrangement size (4 images collage)
        //https://ru.wikipedia.org/wiki/%D0%A0%D0%B0%D0%B7%D0%BC%D0%B5%D1%89%D0%B5%D0%BD%D0%B8%D0%B5
        long factorial1 = getFactorial(size);
        long factorial2 = getFactorial(size - Utility.COLLAGE_IMAGES_COUNT);
        return factorial1 / factorial2;
    }

    private long getFactorial(int number) {
        long factorial = 1;
        if (number > 1) {
            for (int i = 1; i <= number; i++) {
                factorial = factorial * i;
            }
        }
        return factorial;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareCollage(getView());
                }
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putStringArrayList(SHUFFLE_LIST, shuffleList);
        bundle.putSerializable(USED_COMBINATIONS, usedCombinations);
    }
}
