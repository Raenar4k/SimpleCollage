package com.raenarapps.simplecollage.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    public final UserSearch userSearch;
    public final UserMedia userMedia;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InstagramService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InstagramService instagramService = retrofit.create(InstagramService.class);
        userSearch = new UserSearch(instagramService);
        userMedia = new UserMedia(instagramService);
    }
}
