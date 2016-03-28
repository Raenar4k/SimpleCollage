package com.raenarapps.simplecollage.app;

import android.app.Application;

import com.raenarapps.simplecollage.network.RestClient;

public class CollageApplication extends Application {

    private RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();
        restClient = new RestClient();
    }

    public RestClient getRestClient() {
        return restClient;
    }
}
