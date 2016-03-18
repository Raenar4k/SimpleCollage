package com.raenarapps.simplecollage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.raenarapps.simplecollage.network.InstagramService;
import com.raenarapps.simplecollage.pojo.InstagramMedia;
import com.raenarapps.simplecollage.pojo.Item;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageListFragment extends Fragment {
    private static final String TAG = ImageListFragment.class.getSimpleName();
    public static final String JSON_INSTAGRAM_MEDIA = "JSON_INSTAGRAM_MEDIA";
    Button buttonGET;
    private RecyclerView recyclerView;
    private InstagramMedia instagramMedia;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        buttonGET = (Button) rootView.findViewById(R.id.buttonGET);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Item> items = null;

        if (savedInstanceState != null && savedInstanceState.containsKey(JSON_INSTAGRAM_MEDIA)) {
            instagramMedia =
                    new Gson().fromJson(savedInstanceState.getString(JSON_INSTAGRAM_MEDIA), InstagramMedia.class);
            if (instagramMedia != null) {
                items = instagramMedia.getItems();
                sortList(items);
            }
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.instagram.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            InstagramService service = retrofit.create(InstagramService.class);
            Call<InstagramMedia> userPhotos = service.getUserPhotos("appkode");
            Log.d(TAG, "enqueue");
            userPhotos.enqueue(new Callback<InstagramMedia>() {
                @Override
                public void onResponse(Call<InstagramMedia> call, Response<InstagramMedia> response) {
                    instagramMedia = response.body();
                    List<Item> itemList = instagramMedia.getItems();
                    sortList(itemList);
                    recyclerView.setAdapter(new ImageListAdapter(itemList, getContext()));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    Log.d(TAG,"response");
                }

                @Override
                public void onFailure(Call<InstagramMedia> call, Throwable t) {

                }
            });
        }
        recyclerView.setAdapter(new ImageListAdapter(items, getContext()));
        return rootView;
    }

    private void sortList(List<Item> items) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item lhs, Item rhs) {
                return rhs.getLikes().getCount().compareTo(lhs.getLikes().getCount());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (instagramMedia != null ){
            String json = new Gson().toJson(instagramMedia);
            bundle.putString(JSON_INSTAGRAM_MEDIA, json);
        }
    }

}
