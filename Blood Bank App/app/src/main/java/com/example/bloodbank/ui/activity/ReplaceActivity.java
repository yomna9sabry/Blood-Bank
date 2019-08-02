package com.example.bloodbank.ui.activity;



import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.example.bloodbank.R;
import com.example.bloodbank.ui.fragment.SlideFragment;

import static com.example.bloodbank.helper.HelperMathod.getStartFragments;

public class ReplaceActivity extends AppCompatActivity {
    private boolean exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace);

       getStartFragments( getSupportFragmentManager(),R.id.splashActivityReplaceFragment,new SlideFragment());

    }

    @Override
    public void onBackPressed() {
        if (exit){
            finishAffinity();
            super.onBackPressed();
        }
        else {
            Toast.makeText(this, "Press again to close the application", Toast.LENGTH_SHORT).show();
            exit = true;
            new CountDownTimer(3000,1000){
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {

                    exit =false;
                }
            }.start();

        }
    }
    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }
}
