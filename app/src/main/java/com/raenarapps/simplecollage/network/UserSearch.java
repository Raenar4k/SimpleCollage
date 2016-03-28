package com.raenarapps.simplecollage.network;

import com.raenarapps.simplecollage.pojo.SearchResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSearch implements Callback<SearchResult> {
    public Listener listener;
    private InstagramService instagramService;
    private String currentUserName;

    public interface Listener {
        void onSearchResult(SearchResult searchResult, boolean isSuccess, String userNameQuery);
    }

    public UserSearch(InstagramService instagramService) {
        this.instagramService = instagramService;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void clearListener() {
        this.listener = null;
    }

    @Override
    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
        if (listener != null) {
            listener.onSearchResult(response.body(), true, currentUserName);
        }
    }

    @Override
    public void onFailure(Call<SearchResult> call, Throwable t) {
        if (listener != null) {
            listener.onSearchResult(null, false, null);
        }
    }

    public void searchForUser(String userName) {
        currentUserName = userName;
        Call<SearchResult> searchCall = instagramService.searchUsers(currentUserName, InstagramService.CLIENT_ID);
        searchCall.enqueue(this);
    }
}

