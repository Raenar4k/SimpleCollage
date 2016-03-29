package com.raenarapps.simplecollage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.raenarapps.simplecollage.R;
import com.raenarapps.simplecollage.activity.ImageListActivity;
import com.raenarapps.simplecollage.app.CollageApplication;
import com.raenarapps.simplecollage.network.RestClient;
import com.raenarapps.simplecollage.network.UserMedia;
import com.raenarapps.simplecollage.network.UserSearch;
import com.raenarapps.simplecollage.pojo.MediaData;
import com.raenarapps.simplecollage.pojo.RecentMedia;
import com.raenarapps.simplecollage.pojo.SearchData;
import com.raenarapps.simplecollage.pojo.SearchResult;
import com.raenarapps.simplecollage.util.Utility;

import java.util.List;


public class UserFragment extends Fragment implements UserSearch.Listener, UserMedia.Listener {

    private static final String TAG = UserFragment.class.getSimpleName();
    private EditText editTextUsername;
    private ProgressBar progressBar;
    private RestClient restClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        editTextUsername = (EditText) rootView.findViewById(R.id.editTextUsername);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        Button buttonSearch = (Button) rootView.findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForUser(editTextUsername.getText().toString());
            }
        });
        restClient = ((CollageApplication) getActivity().getApplication()).getRestClient();
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(getContext().getString(R.string.activity_user_label));
            }
        return rootView;
    }

    private void searchForUser(String userName) {
        if (userName.length() != 0) {
            progressBar.setVisibility(View.VISIBLE);
            restClient.userSearch.searchForUser(userName);
        }
    }

    private void getUserMedia(String userID) {
        if (userID != null) {
            progressBar.setVisibility(View.VISIBLE);
            restClient.userMedia.getUserMedia(userID);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        restClient.userSearch.setListener(this);
        restClient.userMedia.setListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        restClient.userSearch.clearListener();
        restClient.userMedia.clearListener();
    }

    @Override
    public void onSearchResult(SearchResult searchResult, boolean isSuccess, String userNameQuery) {
        progressBar.setVisibility(View.INVISIBLE);
        Context context = getContext();
        if (isSuccess) {
            if (searchResult != null) {
                List<SearchData> matchesList = searchResult.getData();
                String userId;
                for (int i = 0; i < matchesList.size(); i++) {
                    SearchData possibleMatch = matchesList.get(i);
                    if (possibleMatch.getUsername().equals(userNameQuery)) {
                        userId = possibleMatch.getId();
                        getUserMedia(userId);
                        return;
                    }
                }
                Toast.makeText(context, R.string.toast_request_user_not_found, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, R.string.toast_request_fail, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMediaResult(RecentMedia recentMedia, boolean isSuccess) {
        progressBar.setVisibility(View.INVISIBLE);
        Context context = getContext();
        if (isSuccess) {
            if (recentMedia != null) {
                List<MediaData> itemList = recentMedia.getData();
                if (itemList.size() == 0) {
                    Toast.makeText(context, R.string.toast_request_user_0_images, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, ImageListActivity.class);
                    String json = new Gson().toJson(recentMedia);
                    intent.putExtra(Utility.JSON_INSTAGRAM_MEDIA, json);
                    context.startActivity(intent);
                }
            } else {
                Toast.makeText(context, R.string.toast_request_media_null, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, R.string.toast_request_fail, Toast.LENGTH_SHORT).show();
        }
    }
}
