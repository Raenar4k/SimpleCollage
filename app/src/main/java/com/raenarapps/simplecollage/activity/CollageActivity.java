package com.raenarapps.simplecollage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.raenarapps.simplecollage.R;
import com.raenarapps.simplecollage.fragment.CollageFragment;
import com.raenarapps.simplecollage.util.Utility;

public class CollageActivity extends AppCompatActivity {

    public static final String COLLAGE_FRAGMENT_TAG = "COLLAGE_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(COLLAGE_FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new CollageFragment();
            Intent intent = getIntent();
            if (intent!= null && intent.hasExtra(Utility.SELECTED_URLS_ARRAY)){
                fragment.setArguments(intent.getExtras());
            }
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, COLLAGE_FRAGMENT_TAG)
                .commit();
    }
}
