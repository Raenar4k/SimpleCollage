package com.raenarapps.simplecollage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.raenarapps.simplecollage.pojo.InstagramMedia;
import com.raenarapps.simplecollage.pojo.Item;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ImageListFragment extends Fragment implements
        ImageListAdapter.OnImageClickListener {

    public static final String JSON_INSTAGRAM_MEDIA = "JSON_INSTAGRAM_MEDIA";

    private static final String TAG = ImageListFragment.class.getSimpleName();

    private InstagramMedia instagramMedia;
    private HashMap<Integer, String> selectedImagesMap;
    private int imagesTotalCount;
    private int imagesSelectedCount;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button buttonGET = (Button) rootView.findViewById(R.id.buttonGet);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (savedInstanceState != null && savedInstanceState.containsKey(JSON_INSTAGRAM_MEDIA)) {
            instagramMedia =
                    new Gson().fromJson(savedInstanceState.getString(JSON_INSTAGRAM_MEDIA), InstagramMedia.class);
        } else {
            instagramMedia =
                    new Gson().fromJson(getArguments().getString(JSON_INSTAGRAM_MEDIA), InstagramMedia.class);
        }

        List<Item> items = instagramMedia.getItems();
        sortList(items);
        recyclerView.setAdapter(new ImageListAdapter(items, getContext(), this));
        selectedImagesMap = ((ImageListAdapter) recyclerView.getAdapter()).getSelectedImagesMap();
        imagesTotalCount = items.size();
        imagesSelectedCount = selectedImagesMap.size();
        updateActionBar(imagesSelectedCount, imagesTotalCount);

        buttonGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagesSelectedCount >= Utility.COLLAGE_IMAGES_COUNT) {
                    Collection<String> values = selectedImagesMap.values();
                    String[] selectedUrls = values.toArray(new String[values.size()]);
                    Intent collageIntent = new Intent(getContext(), CollageActivity.class);
                    collageIntent.putExtra(Utility.SELECTED_URLS_ARRAY, selectedUrls);
                    startActivity(collageIntent);
                } else {
                    Snackbar snackbar = Snackbar.make(v, R.string.snackbar_min_count, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
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
    public void onImageClick(HashMap<Integer, String> selectedImagesMap, int totalCount) {
        imagesSelectedCount = selectedImagesMap.size();
        updateActionBar(selectedImagesMap.size(), imagesTotalCount);
    }

    private void updateActionBar(int countSelected, int countTotal) {
        if (getActivity() != null) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(countSelected
                        + getContext().getString(R.string.actionbar_selected_count) + countTotal);
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (instagramMedia != null) {
            String json = new Gson().toJson(instagramMedia);
            bundle.putString(JSON_INSTAGRAM_MEDIA, json);
        }
    }
}
