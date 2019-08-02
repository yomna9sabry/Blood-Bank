package com.example.bloodbank.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbank.R;
import com.example.bloodbank.data.api.ApiServer;
import com.example.bloodbank.data.model.favourite.FavouriteModel;
import com.example.bloodbank.data.model.posts.DataContentPostModel;
import com.example.bloodbank.data.model.posts.PostsModel;
import com.example.bloodbank.ui.fragment.ContentArticlesFragment;

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


public class ArticlesAdapterRecycler extends RecyclerView.Adapter<ArticlesAdapterRecycler.ViewHolder> {

    ArrayList<DataContentPostModel> postsArrayList;

    private ApiServer apiServer;
    Activity context;
    private boolean numFavorite = true;
    private boolean CheckFavorite = true;
    private int postion;

    public ArticlesAdapterRecycler(ArrayList<DataContentPostModel> postsArrayList, Activity context) {
        this.postsArrayList = postsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.set_adapter_articles_recycler_posts, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        apiServer = getClient().create(ApiServer.class);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // set title
        viewHolder.articlesAdapterTitleTxt.setText(postsArrayList.get(i).getTitle());
        // method lode image view post
        LodeImage(context, postsArrayList.get(i).getThumbnailFullPath(),
                viewHolder.articlesAdapterShowImg, viewHolder.articlesAdapterLodeProgressBar);

        // set icon favourite
        if (postsArrayList.get(i).getIsFavourite()) {
            viewHolder.articlesAdapterFavoriteImg.setChecked(postsArrayList.get(i).getIsFavourite());
        }
        postion = i;
        // on click icon favourite if choose true or false
        viewHolder.articlesAdapterFavoriteImg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(" un Liked"," un Liked");
                    // get data all favourite
                    getFavoriteApi(LoadData(context, USER_API_TOKEN), postsArrayList.get(i).getId());
                  } else {
                    //Show "Removed from favourite" toast
                    Log.d(" Liked"," Liked");
                    // get data all un favourite
                    getFavoriteApi(LoadData(context, USER_API_TOKEN), postsArrayList.get(i).getId());
                }
            }
        });

        viewHolder.articlesAdapterShowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("getTitle", postsArrayList.get(i).getTitle());
                bundle.putString("getContent", postsArrayList.get(i).getContent());
                bundle.putString("getThumbnailFullPath", postsArrayList.get(i).getThumbnailFullPath());
                bundle.putBoolean("getIsFavourite", postsArrayList.get(i).getIsFavourite());
                bundle.putString("getCategory", postsArrayList.get(i).getCategory().getName());
                Fragment fragment = new ContentArticlesFragment();
                fragment.setArguments(bundle);
                getStartFragments(((FragmentActivity) context).getSupportFragmentManager(), R.id.ReplaceContentAll
                        , fragment);
            }
        });

    }

    // get data all  favourite
    private void getFavoriteApi(String loadData, final Integer id) {
        apiServer.getFavourite(id, loadData).enqueue(new Callback<FavouriteModel>() {
            @Override
            public void onResponse(Call<FavouriteModel> call, Response<FavouriteModel> response) {

                Log.d("getFavoriteApi", response.message());
                if (response.body().getStatus() == 1) {
                }
            }

            @Override
            public void onFailure(Call<FavouriteModel> call, Throwable t) {

            }
        });
    }

    

    @Override
    public int getItemCount() {
        return postsArrayList.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.articlesAdapterShowImg)
        ImageView articlesAdapterShowImg;
        @BindView(R.id.articlesAdapterTitleTxt)
        TextView articlesAdapterTitleTxt;
        @BindView(R.id.articlesAdapterFavoriteImg)
        CheckBox articlesAdapterFavoriteImg;
        @BindView(R.id.articlesAdapterLodeProgressBar)
        ProgressBar articlesAdapterLodeProgressBar;
        @BindView(R.id.relativeLayout)
        LinearLayout relativeLayout;
        @BindView(R.id.articlesAdapterContent)
        ConstraintLayout articlesAdapterContent;
        View view;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }

}
