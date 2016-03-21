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
import java.util.Date;


public class CollageFragment extends Fragment {
    private static final String TAG = "qwe";
    Button buttonDraw;
    private ImageView imageView2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView1;
        ImageView imageView3;
        ImageView imageView4;
        final View rootView = inflater.inflate(R.layout.fragment_collage, container, false);
        imageView1 = (ImageView) rootView.findViewById(R.id.imageView1);
        imageView2 = (ImageView) rootView.findViewById(R.id.imageView2);
        imageView3 = (ImageView) rootView.findViewById(R.id.imageView3);
        imageView4 = (ImageView) rootView.findViewById(R.id.imageView4);
        String[] urlArray = getArguments().getStringArray(Utility.SELECTED_URLS_ARRAY);

        Picasso.with(getContext()).load(urlArray[0])
                .fit().centerCrop()
                .into(imageView1);
        Picasso.with(getContext()).load(urlArray[1])
                .fit().centerCrop()
                .into(imageView2);
        Picasso.with(getContext()).load(urlArray[2])
                .fit().centerCrop()
                .into(imageView3);
        Picasso.with(getContext()).load(urlArray[3])
                .fit().centerCrop()
                .into(imageView4);

        buttonDraw = (Button) rootView.findViewById(R.id.buttonDraw);
        buttonDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View collageView = rootView.findViewById(R.id.layoutCollage);
                collageView.setDrawingCacheEnabled(true);
                collageView.buildDrawingCache();
                Bitmap drawingCache = collageView.getDrawingCache();
                imageView2.setImageBitmap(drawingCache);
                saveBitmap(drawingCache);
            }
        });

        return rootView;
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
