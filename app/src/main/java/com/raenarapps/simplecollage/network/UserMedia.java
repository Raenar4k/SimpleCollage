package com.raenarapps.simplecollage.network;

import com.raenarapps.simplecollage.pojo.RecentMedia;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMedia implements Callback<RecentMedia>{
    public Listener listener;
    private InstagramService instagramService;

    public interface Listener{
        void onMediaResult(RecentMedia recentMedia, boolean isSuccess);
    }

    public UserMedia(InstagramService instagramService) {
        this.instagramService = instagramService;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void clearListener() {
        this.listener = null;
    }

    @Override
    public void onResponse(Call<RecentMedia> call, Response<RecentMedia> response) {
        if (listener !=null){
            listener.onMediaResult(response.body(), true);
        }
    }

    @Override
    public void onFailure(Call<RecentMedia> call, Throwable t) {
        if (listener !=null){
            listener.onMediaResult(null, false);
        }
    }

    public void getUserMedia(String userID) {
        Call<RecentMedia> recentMediaCall =
                instagramService.getUserRecentMedia(userID, InstagramService.CLIENT_ID);
        recentMediaCall.enqueue(this);
    }
}
