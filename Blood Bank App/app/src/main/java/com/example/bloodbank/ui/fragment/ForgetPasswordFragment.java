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
import com.example.bloodbank.data.model.reset.ResetModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.RetrofitClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferncesManger.SaveData;
import static com.example.bloodbank.data.local.SharedPreferncesManger.setSharedPreferences;
import static com.example.bloodbank.helper.HelperMathod.getStartFragments;


public class ForgetPasswordFragment extends Fragment {
    View view;
    @BindView(R.id.ForgetPasswordFragmentEditUserPhone)
    EditText ForgetPasswordFragmentEditUserPhone;

    @BindView(R.id.ForgetPasswordFragmentBtnSend)
    Button ForgetPasswordFragmentBtnSend;

    Unbinder unbinder;

    ApiServer apiServer;
    public static Login login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_forget_password, container, false);

        unbinder = ButterKnife.bind(this, view);
        // initialize ShardPreferences
        setSharedPreferences(getActivity());

        // class login retrofit
        ClassLoginRetrofit();
        // Class All OnClick
        ClassAllOnClick();

        return view;
    }

    private void ClassAllOnClick() {




    }




    // class login retrofit
    private void ClassLoginRetrofit() {
        ForgetPasswordFragmentBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServer = getClient().create(ApiServer.class);
                Call<ResetModel> call = apiServer.resetPassword(ForgetPasswordFragmentEditUserPhone.getText().toString());
                call.enqueue(new Callback<ResetModel>() {
                    @Override
                    public void onResponse(Call<ResetModel> call, Response<ResetModel> response) {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        if (response.body().getStatus() == 1) {

                            Bundle bundle=new Bundle();
                            bundle.putString("getPinCodeForTest",String.valueOf( response.body().getData().getPinCodeForTest()));
                            bundle.putString("UserPhone",ForgetPasswordFragmentEditUserPhone.getText().toString());
                            Fragment fragment = new NewPasswordFragment();
                            fragment.setArguments(bundle);
                            if (getFragmentManager() != null) {
                                getStartFragments( getFragmentManager(),R.id.splashActivityReplaceFragment,fragment);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResetModel> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
