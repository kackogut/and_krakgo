package com.kacper.and_krakgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kacper.and_krakgo.home.HomeMainActivity;
import com.kacper.and_krakgo.sign_in.SignInActivity;

public class KrakGoApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, HomeMainActivity.class);
        startActivity(intent);
        finish();
    }
}
