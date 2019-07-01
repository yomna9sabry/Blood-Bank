package com.example.jokyom.bloodbank.ui.fragment.userCycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jokyom.bloodbank.R;
import com.example.jokyom.bloodbank.helper.HelperMethod;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LogInFragment extends Fragment {

    Unbinder unbinder;

    public LogInFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.loginFragmentLoginBtn, R.id.loginFragmentCreateAcountBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginFragmentLoginBtn:
                break;
            case R.id.loginFragmentCreateAcountBtn:
                RegisterFragment registerFragment=new RegisterFragment();
                HelperMethod.replace(registerFragment,getActivity().getSupportFragmentManager(),R.id.userCycleFragmentContainer,null,null);

                break;
        }
    }
}
