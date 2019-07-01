package com.example.jokyom.bloodbank.ui.activity;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.jokyom.bloodbank.R;
import com.example.jokyom.bloodbank.helper.HelperMethod;
import com.example.jokyom.bloodbank.ui.fragment.splashCycle.splashFragment;


public class SplashCycleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashFragment spllashFragment=new splashFragment();
        HelperMethod.replace(spllashFragment,getSupportFragmentManager(),R.id.splashCycleFrameLayout,null,null);
       // setContentView(R.layout.fragment_splash);
    }
    @Override
    public  void onBackPressed()
    {
       // super.onBackPressed();
        finish();
    }
}
