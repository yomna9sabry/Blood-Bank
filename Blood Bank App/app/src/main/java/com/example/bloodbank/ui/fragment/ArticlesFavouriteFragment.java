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
import com.example.bloodbank.adapter.ArticlesAdapterRecycler;
import com.example.bloodbank.data.api.ApiServer;
import com.example.bloodbank.data.model.posts.DataContentPostModel;
import com.example.bloodbank.data.model.posts.PostsModel;

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

public class ArticlesFavouriteFragment extends Fragment {

    @BindView(R.id.articlesFavouriteFragmentShowPostRecyclerView)
    RecyclerView articlesFavouriteFragmentShowPostRecyclerView;

    ProgressBar articlesFavouriteFragmentProgBar;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ArticlesAdapterRecycler articlesAdapterRecycler;

    private ApiServer apiServer;
    private View view;
    private ArrayList<DataContentPostModel> postsArrayList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_articles_favourite, container, false);

        unbinder = ButterKnife.bind(this, view);


        inti();
        articlesFavouriteFragmentProgBar.setVisibility(View.VISIBLE);

        getPosts();

        return view;

    }

    // initialize tools
    private void inti() {
        articlesFavouriteFragmentProgBar = view.findViewById(R.id.articlesFavouriteFragmentProgBar);


        postsArrayList = new ArrayList<>();
        apiServer = getClient().create(ApiServer.class);

        // add value tool bar
        ToolBar(getFragmentManager(), getActivity(), toolbar, getResources().getString(R.string.favorite));

    }


    // get all  post
    private void getPosts() {
        try {
            articlesFavouriteFragmentProgBar.setVisibility(View.VISIBLE);
            // get  data  post
            apiServer.getMyFavourite(LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<PostsModel>() {
                @Override
                public void onResponse(Call<PostsModel> call, Response<PostsModel> response) {
                    Log.d("response BloodT ", response.body().getMsg());

                    try {
                        if (response.body().getStatus() == 1) {

                            postsArrayList.addAll(response.body().getData().getData());

                            articlesFavouriteFragmentShowPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                            articlesAdapterRecycler = new ArticlesAdapterRecycler(postsArrayList, getActivity());
                            articlesFavouriteFragmentShowPostRecyclerView.setAdapter(articlesAdapterRecycler);

                        } else {

                            articlesFavouriteFragmentProgBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(getContext(), "Not PaginationData ", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }

                }

                @Override
                public void onFailure(Call<PostsModel> call, Throwable t) {

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
