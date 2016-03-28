package com.raenarapps.simplecollage.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.raenarapps.simplecollage.app.CollageApplication;
import com.raenarapps.simplecollage.network.RestClient;


public abstract class RestActivity extends AppCompatActivity {
    private RestClient restClient;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        restClient = ((CollageApplication) getApplication()).getRestClient();
    }

    public RestClient getRestClient(){
        return restClient;
    }
}
