package com.example.bloodbank.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbank.R;
import com.example.bloodbank.data.api.ApiServer;
import com.example.bloodbank.data.model.donationRequests.DonationRequestsModel;
import com.example.bloodbank.data.model.favourite.FavouriteModel;
import com.example.bloodbank.data.model.notifications.DataNotify;
import com.example.bloodbank.data.model.posts.DataContentPostModel;
import com.example.bloodbank.ui.fragment.ContentDonationFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.RetrofitClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbank.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbank.helper.HelperMathod.LodeImage;
import static com.example.bloodbank.helper.HelperMathod.getStartFragments;


public class NotificationAdapterRecycler extends RecyclerView.Adapter<NotificationAdapterRecycler.ViewHolder> {

    ArrayList<DataNotify> postsArrayList;

    private ApiServer apiServer;
    Activity context;
    private boolean numFavorite = true;
    private int postion;

    public NotificationAdapterRecycler(ArrayList<DataNotify> postsArrayList, Activity context) {
        this.postsArrayList = postsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.set_adapter_recycler_notifcation, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        apiServer = getClient().create(ApiServer.class);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // set title
        viewHolder.notificationAdapterTitleTxt.setText(postsArrayList.get(i).getTitle());
        viewHolder.notificationAdapterTimeTxt.setText(postsArrayList.get(i).getUpdatedAt());

        if (postsArrayList.get(i).getPivotNotify().getIsRead().equals("0")) {
            viewHolder.notification_adapter.setImageResource(R.drawable.icon_notify);
        } else {
            viewHolder.notification_adapter.setImageResource(R.drawable.icon_un_notify);
        }

        viewHolder.notificationAdapterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString("getDonationRequestId", postsArrayList.get(i).getDonationRequestId());
                bundle.putInt("returnResult", 1);
                Fragment fragment = new ContentDonationFragment();
                fragment.setArguments(bundle);
                getStartFragments(((FragmentActivity) context).getSupportFragmentManager(), R.id.ReplaceContentAll
                        , fragment);
             }
        });
    }


    @Override
    public int getItemCount() {
        return postsArrayList.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_adapter)
        ImageView notification_adapter;
        @BindView(R.id.notificationAdapterTimeTxt)
        TextView notificationAdapterTimeTxt;
        @BindView(R.id.notificationAdapterTitleTxt)
        TextView notificationAdapterTitleTxt;
        @BindView(R.id.notificationAdapterLayout)
        LinearLayout notificationAdapterLayout;

        View view;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }

}
