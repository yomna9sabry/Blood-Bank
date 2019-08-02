package com.example.bloodbank.ui.fragment;

import android.support.v4.app.Fragment;

import com.example.bloodbank.ui.activity.BaseActivity;

public class BaseFragment extends Fragment {

    public BaseActivity baseActivity;

    public void setFragmnt(){
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;
    }

    public void onBack(){
        baseActivity = (BaseActivity) getActivity();
        baseActivity.superBackPressed();
    }

}
