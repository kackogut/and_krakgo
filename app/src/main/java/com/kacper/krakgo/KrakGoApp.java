package com.kacper.krakgo;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacper.krakgo.helpers.SharedPreferencesHelper;
import com.kacper.krakgo.helpers.ToastMessageHelper;
import com.kacper.krakgo.screens.home.HomeMainActivity;
import com.kacper.krakgo.screens.sign_in.SignInActivity;

public class KrakGoApp extends AppCompatActivity {
    private static KrakGoApp sInstance;
    private static FirebaseUser sUser;
    private static DatabaseReference sFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sInstance = this;
        sFirebaseDatabase = FirebaseDatabase.getInstance().getReference();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if(SharedPreferencesHelper.getBoolean(SharedPreferencesHelper.REMEMBER_USER)) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user != null) {
                        intent = new Intent(getApplicationContext(), HomeMainActivity.class);
                        sUser = user;
                    }
                    else
                        ToastMessageHelper.showShortToast(R.string.auto_login_error);
                }
                if(intent == null){
                    intent = new Intent(getApplicationContext(), SignInActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 5000);

    }
    public static Context getApplicationCtx(){
        if(sInstance != null)
            return sInstance.getApplicationContext();

            return null;
    }

    public static FirebaseUser getCurrentUser(){
        if(sUser == null)
            sUser = FirebaseAuth.getInstance().getCurrentUser();
        return sUser;
    }

    public static void logout(){
        sUser = null;
        FirebaseAuth.getInstance().signOut();
        SharedPreferencesHelper.saveToSharedPreferences(SharedPreferencesHelper.REMEMBER_USER, false);
    }
    public static DatabaseReference getFirebaseReference(){
        return sFirebaseDatabase;
    }
}
