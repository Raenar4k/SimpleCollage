package com.raenarapps.simplecollage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class CollageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        View rootView = inflater.inflate(R.layout.fragment_collage, container, false);
        imageView1 = (ImageView) rootView.findViewById(R.id.imageView1);
        imageView2 = (ImageView) rootView.findViewById(R.id.imageView2);
        imageView3 = (ImageView) rootView.findViewById(R.id.imageView3);
        imageView4 = (ImageView) rootView.findViewById(R.id.imageView4);
        String[] urlArray = getArguments().getStringArray(Utility.SELECTED_URLS_ARRAY);

        Picasso.with(getContext()).load(urlArray[0]).into(imageView1);
        Picasso.with(getContext()).load(urlArray[1]).into(imageView2);
        Picasso.with(getContext()).load(urlArray[2]).into(imageView3);
        Picasso.with(getContext()).load(urlArray[3]).into(imageView4);
        return rootView;
    }
}
