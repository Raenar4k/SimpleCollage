package com.raenarapps.simplecollage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.raenarapps.simplecollage.network.InstagramService;
import com.raenarapps.simplecollage.pojo.InstagramMedia;
import com.raenarapps.simplecollage.pojo.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserFragment extends Fragment implements Callback<InstagramMedia> {

    private static final String TAG = UserFragment.class.getSimpleName();
    private EditText editTextUsername;
    private ProgressBar progressBar;

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
                getUserMedia(editTextUsername.getText().toString());
            }
        });
        return rootView;
    }

    private void getUserMedia(String userName) {
        if (userName.length() != 0) {
            progressBar.setVisibility(View.VISIBLE);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.instagram.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InstagramService service = retrofit.create(InstagramService.class);
            Call<InstagramMedia> userPhotos = service.getUserPhotos(userName);
            Log.d(TAG, "enqueue");
            userPhotos.enqueue(this);
        }
    }


    @Override
    public void onResponse(Call<InstagramMedia> call, Response<InstagramMedia> response) {
        Context context = getContext();
        if (context != null) {
            progressBar.setVisibility(View.INVISIBLE);
            InstagramMedia instagramMedia = response.body();
            if (instagramMedia == null) {
                Toast.makeText(getContext(), R.string.toast_request_user_not_found, Toast.LENGTH_SHORT).show();
            } else {
                List<Item> itemList = instagramMedia.getItems();
                if (itemList.size() == 0) {
                    Toast.makeText(getContext(), R.string.toast_request_user_0_images, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    String json = new Gson().toJson(instagramMedia);
                    intent.putExtra(Utility.JSON_INSTAGRAM_MEDIA, json);
                    context.startActivity(intent);
                }

            }
        }
    }

    @Override
    public void onFailure(Call<InstagramMedia> call, Throwable t) {
        Context context = getContext();
        if (context != null) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getContext(), R.string.toast_request_fail, Toast.LENGTH_SHORT).show();
        }
    }
}
