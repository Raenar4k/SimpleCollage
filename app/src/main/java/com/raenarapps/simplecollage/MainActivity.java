package com.raenarapps.simplecollage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String IMAGE_LIST_FRAGMENT_TAG = "ImageListFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(IMAGE_LIST_FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new ImageListFragment();
            Bundle bundle = new Bundle();
            String jsonString = getIntent().getStringExtra(Utility.JSON_INSTAGRAM_MEDIA);
            bundle.putString(Utility.JSON_INSTAGRAM_MEDIA, jsonString);
            fragment.setArguments(bundle);
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, IMAGE_LIST_FRAGMENT_TAG)
                .commit();

    }
}
