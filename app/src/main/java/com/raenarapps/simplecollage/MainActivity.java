package com.raenarapps.simplecollage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.raenarapps.simplecollage.network.InstagramService;
import com.raenarapps.simplecollage.pojo.Images;
import com.raenarapps.simplecollage.pojo.InstagramMedia;
import com.raenarapps.simplecollage.pojo.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Button buttonGET;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonGET = (Button) findViewById(R.id.buttonGET);
        textView = (TextView) findViewById(R.id.tv);
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        InstagramMedia instagramMedia = response.body();
                        List<Item> itemList = instagramMedia.getItems();
                        int i = 0;
                        String url = null;
                        while (null == url) {
                            Images images = itemList.get(i).getImages();
                            if (images != null) {
                                Log.d(TAG, "images != null");
                                if (images.getStandardResolution() != null) {
                                    Log.d(TAG, "standardResolution != null");
                                    Log.d(TAG, "i = "+i);
                                    url = images.getStandardResolution().getUrl();
                                }
                            }
                            i++;
                            if (i == 20) {
                                Log.d(TAG, "i = 20");
                                break;
                            }
                        }
                        Picasso.with(getApplicationContext()).load(url).into(imageView);
                    }

                    @Override
                    public void onFailure(Call<InstagramMedia> call, Throwable t) {

                    }
                });

            }
        });
    }
}
