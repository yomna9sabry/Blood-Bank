package com.example.jokyom.bloodbank.ui.fragment.userCycle;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jokyom.bloodbank.R;
import com.example.jokyom.bloodbank.data.model.resetPassword.ResetPassword;
import com.example.jokyom.bloodbank.data.rest.ApiServices;
import com.example.jokyom.bloodbank.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.jokyom.bloodbank.data.rest.RetrofitClient.getClient;


public class ForgetPasswordStep1Fragment extends Fragment {

    @BindView(R.id.ForgetFragmentTextInputPasswordFragmentSendBtn)
    Button ForgetFragmentTextInputPasswordFragmentSendBtn;
    Unbinder unbinder;
    @BindView(R.id.ForgetFragmentTextInputPasswordFragmentTextInputPhone)
    TextInputLayout ForgetFragmentTextInputPasswordFragmentTextInputPhone;

    public ForgetPasswordStep1Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_pass_step1, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

        ApiServices apiServices = getClient().create(ApiServices.class);
        apiServices.resetPassword(ForgetFragmentTextInputPasswordFragmentTextInputPhone.toString()).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
               ForgetPasswordStep2Fragment forgetPasswordStep2Fragment=new ForgetPasswordStep2Fragment();
                HelperMethod.replace(forgetPasswordStep2Fragment,getActivity().getSupportFragmentManager(),R.id.userCycleFragmentContainer,null,null);

            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {
                Toast.makeText(getActivity(), "Something get wrong", Toast.LENGTH_LONG).show();


            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ForgetFragmentTextInputPasswordFragmentSendBtn)
    public void onViewClicked() {
        ForgetPasswordStep2Fragment forgetPasswordStep2Fragment = new ForgetPasswordStep2Fragment();
        HelperMethod.replace(forgetPasswordStep2Fragment, getActivity().getSupportFragmentManager(), R.id.userCycleFragmentContainer, null, null);

    }
}
