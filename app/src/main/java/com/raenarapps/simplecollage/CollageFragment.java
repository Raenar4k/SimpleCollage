package com.raenarapps.simplecollage;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;


public class CollageFragment extends Fragment {
    private static final String TAG = CollageFragment.class.getSimpleName();
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private Set<Integer> usedCombinations;
    private ArrayList<String> shuffleList;
    private long combinationsCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_collage, container, false);
        imageView1 = (ImageView) rootView.findViewById(R.id.imageView1);
        imageView2 = (ImageView) rootView.findViewById(R.id.imageView2);
        imageView3 = (ImageView) rootView.findViewById(R.id.imageView3);
        imageView4 = (ImageView) rootView.findViewById(R.id.imageView4);
        String[] urlArray = getArguments().getStringArray(Utility.SELECTED_URLS_ARRAY);

        shuffleList = new ArrayList<>(Arrays.asList(urlArray));
        combinationsCount = getCombinationsCount(shuffleList.size());
        usedCombinations = new TreeSet<>();
        usedCombinations.add(shuffleList.hashCode());

        Picasso.with(getContext()).load(shuffleList.get(0))
                .fit().centerCrop()
                .into(imageView1);
        Picasso.with(getContext()).load(shuffleList.get(1))
                .fit().centerCrop()
                .into(imageView2);
        Picasso.with(getContext()).load(shuffleList.get(2))
                .fit().centerCrop()
                .into(imageView3);
        Picasso.with(getContext()).load(shuffleList.get(3))
                .fit().centerCrop()
                .into(imageView4);

        Button buttonDraw = (Button) rootView.findViewById(R.id.buttonDraw);
        buttonDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shufflePictures();

//                View collageView = rootView.findViewById(R.id.layoutCollage);
//                collageView.setDrawingCacheEnabled(true);
//                collageView.buildDrawingCache();
//                Bitmap drawingCache = collageView.getDrawingCache();
//                imageView2.setImageBitmap(drawingCache);
//                saveBitmap(drawingCache);
            }
        });
        return rootView;
    }

    private void shufflePictures() {
        if (usedCombinations.size() < combinationsCount) {
            boolean combinationFound = false;
            do {
                Collections.shuffle(shuffleList);
                int hashCodeNew = shuffleList.hashCode();
                if (!usedCombinations.contains(hashCodeNew)) {
                    usedCombinations.add(hashCodeNew);
                    Picasso.with(getContext()).load(shuffleList.get(0))
                            .fit().centerCrop()
                            .into(imageView1);
                    Picasso.with(getContext()).load(shuffleList.get(1))
                            .fit().centerCrop()
                            .into(imageView2);
                    Picasso.with(getContext()).load(shuffleList.get(2))
                            .fit().centerCrop()
                            .into(imageView3);
                    Picasso.with(getContext()).load(shuffleList.get(3))
                            .fit().centerCrop()
                            .into(imageView4);
                    combinationFound = true;
                }
            } while (!combinationFound);
        } else {
            Toast.makeText(getContext(), "all unique combinations have been used", Toast.LENGTH_SHORT).show();
        }
    }

    private long getCombinationsCount(int size) {
        //count = n!/(n-k)!
        // n - number of elements (selected images count), k - arrangement size (4 images collage)
        //https://ru.wikipedia.org/wiki/%D0%A0%D0%B0%D0%B7%D0%BC%D0%B5%D1%89%D0%B5%D0%BD%D0%B8%D0%B5
        long factorial1 = getFactorial(size);
        long factorial2 = getFactorial(size - Utility.COLLAGE_IMAGES_COUNT);
        return factorial1 / factorial2;
    }

    private long getFactorial(int number) {
        long factorial = 1;
        if (number > 1) {
            for (int i = 1; i <= number; i++) {
                factorial = factorial * i;
            }
        }
        return factorial;
    }


    public void saveBitmap(Bitmap bitmap) {
        File directory = getContext().getDir("collages", Context.MODE_PRIVATE);
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        String fileName = "Collage_" + timeStamp + ".png";
        File newBitmapFile = new File(directory, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newBitmapFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(getContext(), "Saved to " + newBitmapFile.getAbsolutePath(),
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
