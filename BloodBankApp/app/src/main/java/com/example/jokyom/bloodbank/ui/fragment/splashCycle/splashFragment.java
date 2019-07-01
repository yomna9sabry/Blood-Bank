package com.example.jokyom.bloodbank.ui.fragment.splashCycle;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jokyom.bloodbank.R;
import com.example.jokyom.bloodbank.helper.HelperMethod;


public class splashFragment extends Fragment {

    private long SPLASH_DISPLAY_LENGTH=2000;

    public splashFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SliderFragment sliderFragment=new SliderFragment();
                HelperMethod.replace(sliderFragment,getActivity().getSupportFragmentManager(),R.id.splashCycleFrameLayout,null,null);

                /* Create an Intent that will start the Menu-Activity. 
                Intent mainIntent = new Intent(Splash.this,Menu.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();*/
            }
        }, SPLASH_DISPLAY_LENGTH);
        return view;


    }

}