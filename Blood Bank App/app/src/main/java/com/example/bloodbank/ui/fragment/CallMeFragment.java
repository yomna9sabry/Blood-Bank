package com.example.bloodbank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbank.R;
import com.example.bloodbank.data.api.ApiServer;
import com.example.bloodbank.data.model.contact.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.RetrofitClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbank.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbank.data.local.SharedPreferncesManger.USER_EMAIL;
import static com.example.bloodbank.data.local.SharedPreferncesManger.USER_PHONE;
import static com.example.bloodbank.helper.HelperMathod.ToolBar;

public class CallMeFragment extends Fragment {

    View view;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragmentCallMeTitleEdit)
    EditText fragmentCallMeTitleEdit;
    @BindView(R.id.fragmentCallMeMessageEdit)
    EditText fragmentCallMeMessageEdit;
    @BindView(R.id.fragmentCallMeSendBtn)
    Button fragmentCallMeSendBtn;
    Unbinder unbinder;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.fragmentCallMeLogo)
    ImageView fragmentCallMeLogo;
    @BindView(R.id.fragmentCallMePhoneTv)
    TextView fragmentCallMePhoneTv;
    @BindView(R.id.fragmentCallMeMailTv)
    TextView fragmentCallMeMailTv;
    ProgressBar fragmentCallMeProgressBar;

    private ApiServer apiServer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_call_me, container, false);


        unbinder = ButterKnife.bind(this, view);
        // initializer tools
        inti();

        return view;
    }

    // initializer tools
    private void inti() {
        fragmentCallMeProgressBar = view.findViewById(R.id.fragmentCallMeProgressBar);

        fragmentCallMeProgressBar.setVisibility(View.INVISIBLE);

        // add value tool bar
        ToolBar(getFragmentManager(),getActivity(), toolbar, getResources().getString(R.string.call_me));
        apiServer = getClient().create(ApiServer.class);



        fragmentCallMePhoneTv.setText(LoadData(getActivity(), USER_PHONE));
        fragmentCallMeMailTv.setText(LoadData(getActivity(), USER_EMAIL));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.fragmentCallMeSendBtn)
    public void onViewClicked() {
        SendMessage();

        fragmentCallMePhoneTv.setText("");
        fragmentCallMeMailTv.setText("");
    }

    //send message
    public void SendMessage() {
        fragmentCallMeProgressBar.setVisibility(View.VISIBLE);
        // get  PaginationData governorates
        apiServer.SendContact(LoadData(getActivity(), USER_API_TOKEN), fragmentCallMeTitleEdit.getText().toString()
                , fragmentCallMeMessageEdit.getText().toString()).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {

                if (response.body().getStatus() == 1) {
                    fragmentCallMeProgressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                else {
                    fragmentCallMeProgressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {

            }
        });

    }
}
