package com.example.bloodbank.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bloodbank.R;
import com.example.bloodbank.data.api.ApiServer;
import com.example.bloodbank.data.model.login.Login;
import com.example.bloodbank.data.model.new_password.NewPasswordModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.RetrofitClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferncesManger.SaveData;
import static com.example.bloodbank.data.local.SharedPreferncesManger.clean;
import static com.example.bloodbank.data.local.SharedPreferncesManger.setSharedPreferences;
import static com.example.bloodbank.helper.HelperMathod.checkCorrespondPassword;
import static com.example.bloodbank.helper.HelperMathod.checkLengthPassword;
import static com.example.bloodbank.helper.HelperMathod.getStartFragments;


public class NewPasswordFragment extends Fragment {
    View view;
    @BindView(R.id.newPasswordFragmentEditUserCodePin)
    EditText newPasswordFragmentEditUserCodePin;

    @BindView(R.id.NewPasswordFragmentEditUserNewPassword)
    EditText NewPasswordFragmentEditUserNewPassword;

    @BindView(R.id.NewPasswordFragmentEditUserConfirmPassword)
    EditText NewPasswordFragmentEditUserConfirmPassword;

    @BindView(R.id.NewPasswordFragmentBtnNewPassword)
    Button NewPasswordFragmentBtnNewPassword;

    Unbinder unbinder;


    ApiServer apiServer;
    public static Login login;
    private String getPinCodeForTest;
    private String UserPhone;
    private String newPassword, CodePin, ConfirmPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_new_password, container, false);

        unbinder = ButterKnife.bind(this, view);

        inti();

        // initialize ShardPreferences
        setSharedPreferences(getActivity());

        // class login retrofit
        ClassLoginRetrofit();
        // Class All OnClick
        ClassAllOnClick();
        return view;
    }

    private void inti() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            getPinCodeForTest = bundle.getString("getPinCodeForTest");
            UserPhone = bundle.getString("UserPhone");
        }


    }

    private void ClassAllOnClick() {


    }


    // class login retrofit
    private void ClassLoginRetrofit() {
        NewPasswordFragmentBtnNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = NewPasswordFragmentEditUserNewPassword.getText().toString();
                CodePin = newPasswordFragmentEditUserCodePin.getText().toString();
                ConfirmPassword = NewPasswordFragmentEditUserConfirmPassword.getText().toString();


            Toast.makeText(getContext(), getPinCodeForTest + "--"  + CodePin, Toast.LENGTH_SHORT).show();

                if (getPinCodeForTest.equals(CodePin)) {

                    if (checkCorrespondPassword(newPassword,ConfirmPassword)&& checkLengthPassword(newPassword)) {

                        apiServer = getClient().create(ApiServer.class);
                        Call<NewPasswordModel> call = apiServer.newPassword(newPassword, ConfirmPassword, CodePin, UserPhone);
                        call.enqueue(new Callback<NewPasswordModel>() {
                            @Override
                            public void onResponse(Call<NewPasswordModel> call, Response<NewPasswordModel> response) {
                                Toast.makeText(getContext(), response.body().getMsg()   , Toast.LENGTH_SHORT).show();

                                if (response.body().getStatus() == 1) {

                                    clean(getActivity());

                                    if (getFragmentManager() != null) {
                                        getStartFragments( getFragmentManager(),R.id.splashActivityReplaceFragment,new LoginFragment());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<NewPasswordModel> call, Throwable t) {
                                newPasswordFragmentEditUserCodePin.setError(getResources().getString(R.string.confirmation_code_wrong));
                            }
                        });

                    } else {
                        if (!checkCorrespondPassword(newPassword,ConfirmPassword)) {
                            NewPasswordFragmentEditUserConfirmPassword.setError(getResources().getString(R.string.number_does_not_correspond));
                        }
                        if (!checkLengthPassword(newPassword)) {
                            NewPasswordFragmentEditUserNewPassword.setError(getResources().getString(R.string.It_should_be_the_largest6));

                        }
                    }

                } else {
                    newPasswordFragmentEditUserCodePin.setError(getResources().getString(R.string.confirmation_number_error));
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
