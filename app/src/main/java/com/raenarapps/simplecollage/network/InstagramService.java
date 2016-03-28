package com.raenarapps.simplecollage.network;

import com.raenarapps.simplecollage.pojo.RecentMedia;
import com.raenarapps.simplecollage.pojo.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InstagramService {
    String BASE_URL = "https://api.instagram.com/v1/";
    String CLIENT_ID = "9734d32bcee14651829e7b2bed26b4c3";

    @GET("users/{user-id}/media/recent/")
//    https://api.instagram.com/v1/users/{user-id}/media/recent/?client_id=CLIENT-ID
    Call<RecentMedia> getUserRecentMedia(@Path("user-id") String userID,
                                         @Query("client_id") String clientID);

    @GET("users/search")
//    https://api.instagram.com/v1/users/search?q=jack&client_id=CLIENT-ID
    Call<SearchResult> searchUsers(@Query("q") String query,
                                   @Query("client_id") String clientID);
}
