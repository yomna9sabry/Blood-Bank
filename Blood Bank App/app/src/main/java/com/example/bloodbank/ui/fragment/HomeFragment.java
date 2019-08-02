package com.example.bloodbank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.PageHomeNavagationAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {


    @BindView(R.id.homeNavgationTabBar)
    TabLayout homeNavgationTabBar;
    @BindView(R.id.homeNavgationViewPager)
    ViewPager homeNavgationViewPager;
    Unbinder unbinder;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);




        unbinder = ButterKnife.bind(this, view);

        initTabBar();

        return view;

    }

    private void initTabBar() {

        homeNavgationTabBar.addTab(homeNavgationTabBar.newTab().setText(getResources().getString(R.string.articles)));
        homeNavgationTabBar.addTab(homeNavgationTabBar.newTab().setText(getResources().getString(R.string.requst_donation)));

        homeNavgationTabBar.setTabGravity(TabLayout.GRAVITY_FILL);

        PageHomeNavagationAdapter pageHomeNavagationAdapter = new PageHomeNavagationAdapter(getFragmentManager(), homeNavgationTabBar.getTabCount());
        homeNavgationViewPager.setAdapter(pageHomeNavagationAdapter);

        int tabIconColor = ContextCompat.getColor(getContext(), R.color.color_tab_bar_line_select);
        homeNavgationTabBar.setSelectedTabIndicatorColor(tabIconColor);

        homeNavgationTabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                homeNavgationViewPager.setCurrentItem(tab.getPosition());

                int tabIconColor = ContextCompat.getColor(getContext(), R.color.color_tab_bar_line_select);
                homeNavgationTabBar.setSelectedTabIndicatorColor(tabIconColor);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getContext(), R.color.white);
                homeNavgationTabBar.setSelectedTabIndicatorColor(tabIconColor);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        homeNavgationViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(homeNavgationTabBar));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
