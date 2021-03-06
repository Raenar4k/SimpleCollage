package com.raenarapps.simplecollage.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.raenarapps.simplecollage.R;
import com.raenarapps.simplecollage.fragment.UserFragment;

public class UserActivity extends RestActivity {

    public static final String USER_FRAGMENT_TAG = "USER_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(USER_FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new UserFragment();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, USER_FRAGMENT_TAG)
                .commit();
    }
}
