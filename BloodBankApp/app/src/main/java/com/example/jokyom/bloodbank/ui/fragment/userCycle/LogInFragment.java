package com.example.jokyom.bloodbank.ui.fragment.userCycle;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jokyom.bloodbank.R;
import com.example.jokyom.bloodbank.data.model.governments.Governments;
import com.example.jokyom.bloodbank.data.model.user.Login;
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


public class LogInFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.loginFragmentTextInputPhone)
    TextInputLayout loginFragmentTextInputPhone;
    @BindView(R.id.loginFragmentTextInputPassword)
    TextInputLayout loginFragmentTextInputPassword;

    private ApiServices apiServices;

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

        apiServices = getClient().create(ApiServices.class);
        apiServices.login().enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
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
                userLogin();
                break;
            case R.id.loginFragmentCreateAcountBtn:
                RegisterFragment registerFragment = new RegisterFragment();
                HelperMethod.replace(registerFragment, getActivity().getSupportFragmentManager(), R.id.userCycleFragmentContainer, null, null);

                break;
        }
    }

    public void userLogin() {
        String phone=loginFragmentTextInputPhone.getEditText().toString().trim();
        String password=loginFragmentTextInputPassword.getEditText().toString().trim();
        if(loginFragmentTextInputPhone.toString()=="")
        {
            loginFragmentTextInputPhone.setError("phone is reguired");
            loginFragmentTextInputPhone.requestFocus();
            return;
        }
        if(loginFragmentTextInputPassword.toString()=="")
        {
            loginFragmentTextInputPassword.setError("phone is reguired");
            loginFragmentTextInputPassword.requestFocus();
            return;
        }


    }
}
