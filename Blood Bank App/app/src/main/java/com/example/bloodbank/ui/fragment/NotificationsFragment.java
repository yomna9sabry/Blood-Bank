package com.example.bloodbank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.NotificationAdapterRecycler;
import com.example.bloodbank.data.api.ApiServer;
import com.example.bloodbank.data.model.notifications.DataNotify;
import com.example.bloodbank.data.model.notifications.Notifications;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.RetrofitClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbank.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbank.helper.HelperMathod.ToolBar;

public class NotificationsFragment extends Fragment {

    @BindView(R.id.notificationsFragmentShowPostRecyclerView)
    RecyclerView notificationsFragmentShowPostRecyclerView;
     ProgressBar notificationsFragmentFavouriteFragmentProgBar;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private NotificationAdapterRecycler notificationAdapterRecycler;

    private ApiServer apiServer;
    private View view;
    private ArrayList<DataNotify> notificationsArrayList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notifications, container, false);

        unbinder = ButterKnife.bind(this, view);

        inti();

        getNotifications();

        return view;

    }

    // initialize tools
    private void inti() {
        notificationsFragmentFavouriteFragmentProgBar = view.findViewById(R.id.notificationsFragmentFavouriteFragmentProgBar);
        notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.INVISIBLE);

        notificationsArrayList = new ArrayList<>();
        apiServer = getClient().create(ApiServer.class);

        // add value tool bar
        ToolBar(getFragmentManager(),getActivity(), toolbar, getResources().getString(R.string.notify));

    }


    // get all  post
    private void getNotifications() {
        try {
            // get  PaginationData  post
            apiServer.getNotifications(LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<Notifications>() {
                @Override
                public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                    Log.d(" notifications 5", response.body().getMsg());

                    try {
                        notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.VISIBLE);

                        if (response.body().getStatus() == 1) {

                            notificationsArrayList.addAll(response.body().getDataNotifyPage().getData());

                            notificationAdapterRecycler = new NotificationAdapterRecycler(notificationsArrayList, getActivity());

                            notificationsFragmentShowPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                            notificationsFragmentShowPostRecyclerView.setAdapter(notificationAdapterRecycler);

                            notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.INVISIBLE);

                        } else {

                            notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(getContext(), "Not PaginationData ", Toast.LENGTH_SHORT).show();

                        }

                    }catch (Exception e){
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<Notifications> call, Throwable t) {
                    Log.d("Throwable",t.getMessage());

                }
            });

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
