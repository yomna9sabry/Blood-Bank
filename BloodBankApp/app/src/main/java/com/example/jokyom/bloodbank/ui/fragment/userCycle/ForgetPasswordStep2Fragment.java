package com.example.jokyom.bloodbank.ui.fragment.userCycle;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jokyom.bloodbank.R;
import com.example.jokyom.bloodbank.data.rest.ApiServices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.jokyom.bloodbank.data.rest.RetrofitClient.getClient;


public class ForgetPasswordStep2Fragment extends Fragment {

    @BindView(R.id.ForgetFragmentStep2TextInputPasswordFragmentChangPassBtn)
    Button ForgetFragmentStep2TextInputPasswordFragmentChangPassBtn;
    Unbinder unbinder;
    @BindView(R.id.ForgetFragmentStep2TextView)
    TextView ForgetFragmentStep2TextView;
    @BindView(R.id.ForgetFragmentStep2TextInputPasswordFragmentCodeVerification)
    TextInputLayout ForgetFragmentStep2TextInputPasswordFragmentCodeVerification;
    @BindView(R.id.ForgetFragmentStep2TextInputPasswordFragmentNewPassword)
    TextInputLayout ForgetFragmentStep2TextInputPasswordFragmentNewPassword;
    @BindView(R.id.ForgetFragmentStep2TextInputPasswordFragmentNewPasswordConfirm)
    TextInputLayout ForgetFragmentStep2TextInputPasswordFragmentNewPasswordConfirm;

    public ForgetPasswordStep2Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_pass_step2, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ForgetFragmentStep2TextInputPasswordFragmentChangPassBtn)
    public void onViewClicked() {
        ApiServices apiServices = getClient().create(ApiServices.class);
        apiServices.newPassword(ForgetFragmentStep2TextView.toString(),ForgetFragmentStep2TextInputPasswordFragmentCodeVerification.toString()
        ,ForgetFragmentStep2TextInputPasswordFragmentNewPassword.toString(),ForgetFragmentStep2TextInputPasswordFragmentNewPasswordConfirm.toString());
    }
}
