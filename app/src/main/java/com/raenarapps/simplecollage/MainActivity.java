package com.raenarapps.simplecollage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.raenarapps.simplecollage.network.InstagramService;
import com.raenarapps.simplecollage.pojo.InstagramMedia;
import com.raenarapps.simplecollage.pojo.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Button buttonGET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonGET = (Button) findViewById(R.id.buttonGET);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

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
                        recyclerView.setAdapter(new ImageListAdapter(itemList, getApplicationContext()));
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }

                    @Override
                    public void onFailure(Call<InstagramMedia> call, Throwable t) {

                    }
                });

            }
        });
    }
}
