package com.example.jokyom.bloodbank.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jokyom.bloodbank.R;
import com.example.jokyom.bloodbank.helper.HelperMethod;
import com.example.jokyom.bloodbank.ui.fragment.userCycle.LogInFragment;

public class UserCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);
        LogInFragment logInFragment=new LogInFragment();
        HelperMethod.replace(logInFragment,getSupportFragmentManager(),R.id.userCycleFragmentContainer,null,null);

    }
}
