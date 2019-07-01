package com.example.jokyom.bloodbank.ui.fragment.splashCycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jokyom.bloodbank.R;
import com.example.jokyom.bloodbank.ui.activity.UserCycleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SliderFragment extends Fragment {

   /* public void SliderImageChange(View view)
    {

    }*/
    Unbinder unbinder;

    public SliderFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Slider_Fragment_Btn_Skip)
    public void onViewClicked() {
        Intent intent=new Intent(getActivity(),UserCycleActivity.class);
        getActivity().startActivity(intent);

        
    }
}