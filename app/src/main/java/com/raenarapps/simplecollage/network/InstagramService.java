package com.raenarapps.simplecollage.network;

import com.raenarapps.simplecollage.pojo.InstagramMedia;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InstagramService {
    @GET("/{user-id}/media")
//    https://api.instagram.com/v1/users/{user-id}/?access_token=ACCESS-TOKEN
    Call<InstagramMedia> getUserPhotos (@Path("user-id") String user);
}
