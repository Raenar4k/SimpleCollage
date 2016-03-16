package com.raenarapps.simplecollage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
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
    }
}
