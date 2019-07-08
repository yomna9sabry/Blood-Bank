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
import com.example.jokyom.bloodbank.data.model.register.Register;
import com.example.jokyom.bloodbank.data.rest.ApiServices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.jokyom.bloodbank.data.rest.RetrofitClient.getClient;


public class RegisterFragment extends Fragment {


    @BindView(R.id.RegisterFragmentTextInputName)
    TextInputLayout RegisterFragmentTextInputName;
    @BindView(R.id.RegisterFragmentTextInputEmail)
    TextInputLayout RegisterFragmentTextInputEmail;
    @BindView(R.id.RegisterFragmentTextInputBirth)
    TextInputLayout RegisterFragmentTextInputBirth;
    @BindView(R.id.RegisterFragmentTextInputBlood)
    TextInputLayout RegisterFragmentTextInputBlood;
    @BindView(R.id.RegisterFragmentTextInputLastDonation)
    TextInputLayout RegisterFragmentTextInputLastDonation;
    @BindView(R.id.RegisterFragmentTextInputTerritory)
    TextInputLayout RegisterFragmentTextInputTerritory;
    @BindView(R.id.RegisterFragmentTextInputCity)
    TextInputLayout RegisterFragmentTextInputCity;
    @BindView(R.id.RegisterFragmentTextInputPhone)
    TextInputLayout RegisterFragmentTextInputPhone;
    @BindView(R.id.RegisterFragmentTextInputPassword)
    TextInputLayout RegisterFragmentTextInputPassword;
    @BindView(R.id.RegisterFragmentTextInputPasswordConfirm)
    TextInputLayout RegisterFragmentTextInputPasswordConfirm;
    @BindView(R.id.RegisterFragmentTextInputRegisterBtn)
    Button RegisterFragmentTextInputRegisterBtn;

    Unbinder unbinder;

    public RegisterFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        userRegister();
        ApiServices apiServices = getClient().create(ApiServices.class);
        apiServices.register(
                RegisterFragmentTextInputName.getEditText().toString()
                ,RegisterFragmentTextInputEmail.getEditText().toString()
                ,RegisterFragmentTextInputBirth.getEditText().toString()
                ,Integer.parseInt(RegisterFragmentTextInputCity.getEditText().toString())
                ,RegisterFragmentTextInputLastDonation.getEditText().toString()
                ,RegisterFragmentTextInputTerritory.getEditText().toString()
                ,RegisterFragmentTextInputPhone.getEditText().toString()
                ,RegisterFragmentTextInputPassword.getEditText().toString()
                ,Integer.parseInt(RegisterFragmentTextInputBlood.getEditText().toString())).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {

            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_LONG).show();

            }
        });

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.RegisterFragmentTextInputRegisterBtn)
    public void onViewClicked() {
        userRegister();
    }

    private void userRegister() {
        if(RegisterFragmentTextInputName.toString()=="")
        {
            RegisterFragmentTextInputName.setError("Name is reguired");
            RegisterFragmentTextInputName.requestFocus();
            return;
        }
        if(RegisterFragmentTextInputEmail.toString()=="")
        {
            RegisterFragmentTextInputEmail.setError("Email is reguired");
            RegisterFragmentTextInputEmail.requestFocus();
            return;
        }
        if(RegisterFragmentTextInputBirth.toString()=="")
        {
            RegisterFragmentTextInputBirth.setError("Birth date is reguired");
            RegisterFragmentTextInputBirth.requestFocus();
            return;
        }
        if(RegisterFragmentTextInputBlood.toString()=="")
        {
            RegisterFragmentTextInputBlood.setError("Blood type is reguired");
            RegisterFragmentTextInputBlood.requestFocus();
            return;
        }
        if(RegisterFragmentTextInputTerritory.toString()=="")
        {
            RegisterFragmentTextInputTerritory.setError("Territory  is reguired");
            RegisterFragmentTextInputTerritory.requestFocus();
            return;
        }
        if(RegisterFragmentTextInputLastDonation.toString()=="")
        {
            RegisterFragmentTextInputLastDonation.setError("Last Donation  is reguired");
            RegisterFragmentTextInputLastDonation.requestFocus();
            return;
        }
        if(RegisterFragmentTextInputCity.toString()=="")
        {
            RegisterFragmentTextInputCity.setError("city  is reguired");
            RegisterFragmentTextInputCity.requestFocus();
            return;
        }
        if(RegisterFragmentTextInputPhone.toString()=="")
        {
            RegisterFragmentTextInputPhone.setError("Phone  is reguired");
            RegisterFragmentTextInputPhone.requestFocus();
            return;
        }
        if(RegisterFragmentTextInputCity.toString()=="")
        {
            RegisterFragmentTextInputCity.setError("city  is reguired");
            RegisterFragmentTextInputCity.requestFocus();
            return;
        }

    }
}
