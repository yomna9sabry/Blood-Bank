package com.example.jokyom.bloodbank.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.example.jokyom.bloodbank.R;
import com.example.jokyom.bloodbank.ui.fragment.splashCycle.splashFragment;

public class HelperMethod {
    public static void replace(Fragment fragment, FragmentManager supportFragmentManager, int id, TextView toolBarTitle, String  title)
    {
        FragmentTransaction fragmentTransaction=supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if(toolBarTitle!=null)
        {
            toolBarTitle.setText(title);
        }
    }
}
