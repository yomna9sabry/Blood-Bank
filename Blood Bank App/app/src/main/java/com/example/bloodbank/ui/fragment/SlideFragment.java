package com.example.bloodbank.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.SliderAdapter;
import com.example.bloodbank.data.model.SliderModel;
import com.example.bloodbank.ui.activity.HomeNavgation;

import java.util.ArrayList;

import static com.example.bloodbank.data.local.SharedPreferncesManger.KEY_IS_CHECK_BOX;
import static com.example.bloodbank.data.local.SharedPreferncesManger.LoadBoolean;
import static com.example.bloodbank.data.local.SharedPreferncesManger.setSharedPreferences;
import static com.example.bloodbank.helper.HelperMathod.getStartFragments;

public class SlideFragment extends Fragment {
    View view;

    ViewPager fragmentSliderViewPager;
    Button sliderFragmentBtn;
    ArrayList<SliderModel> arrayList;
    private static final long SLIDER_TIMER = 2000;
    private boolean isCountDownTimerActive;
    private Handler handler;

    private int currentPage = 0;
    Runnable runnable = (new Runnable() {

        @Override
        public void run() {

            if (!isCountDownTimerActive) {

                automateSlider();

             }

             handler.postDelayed(runnable, SLIDER_TIMER);
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_slider, container, false);

        inti();
        arrayList = new ArrayList<>();
        arrayList.add(new SliderModel(R.drawable.img_slider));
        arrayList.add(new SliderModel(R.drawable.img_slider2));
        SliderAdapter sliderAdapter = new SliderAdapter(getContext(), arrayList);

        fragmentSliderViewPager.setAdapter(sliderAdapter);
        handler = new Handler();

        handler.postDelayed(runnable, 3000);
        runnable.run();

        return view;
    }

    private void inti() {
        fragmentSliderViewPager = view.findViewById(R.id.fragmentSliderViewPager);
        sliderFragmentBtn = view.findViewById(R.id.sliderFragmentBtn);

        onClick();
    }

    private void onClick() {
        // initialize ShardPreferences
        setSharedPreferences(getActivity());

        sliderFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check is checkBox is Checked
                if (LoadBoolean(getActivity(), KEY_IS_CHECK_BOX,false)) {
                    Intent intent = new Intent(getContext(), HomeNavgation.class);
                    startActivity(intent);
                } else {

                    if (getFragmentManager() != null) {
                        getStartFragments( getFragmentManager(),R.id.splashActivityReplaceFragment,new LoginFragment());
                    }

                }


            }
        });
    }

    private void automateSlider() {
        isCountDownTimerActive = true;
        new CountDownTimer(SLIDER_TIMER, 2000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                currentPage = currentPage + 1;

                if (currentPage == 4) {
                    currentPage = 0; // if it's last Image, let it go to the first image
                }

                fragmentSliderViewPager.setCurrentItem(currentPage);
                isCountDownTimerActive = false;
            }
        }.start();
    }


    public void OnClickSkip(View view){

    }
}
