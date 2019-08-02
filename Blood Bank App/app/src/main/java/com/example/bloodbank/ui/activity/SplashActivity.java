package com.example.bloodbank.ui.activity;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bloodbank.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    private void getShowSlider() {
        // start Slider Fragment == 2000 mills
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    // start Splash Fragment
                    startActivity(new Intent(SplashActivity.this, ReplaceActivity.class));

                    //getStartFragments( getSupportFragmentManager(),R.id.splashActivityReplaceFragment,new SlideFragment());
                } catch (Exception e) {
                    e.getMessage();
                }

            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {

            getShowSlider();

        } catch (Exception e) {
            e.getMessage();
        }

    }
}
