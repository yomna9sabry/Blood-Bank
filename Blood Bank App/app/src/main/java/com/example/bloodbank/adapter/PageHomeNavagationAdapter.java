package com.example.bloodbank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.bloodbank.ui.fragment.ArticlesFragment;
import com.example.bloodbank.ui.fragment.DonationRequestsHomeFragment;

public class PageHomeNavagationAdapter extends FragmentStatePagerAdapter {

    int postion;
    public PageHomeNavagationAdapter(FragmentManager fm,int postion) {
        super(fm);
         this.postion =postion;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){

            case 0:
                return new ArticlesFragment();
            case 1:
                return new DonationRequestsHomeFragment();
            default:
                return null;
        }
     }

    @Override
    public int getCount() {
        return postion;
    }
}
