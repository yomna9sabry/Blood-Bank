package com.example.bloodbank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.AdapterSpinner;
import com.example.bloodbank.adapter.ArticlesAdapterRecycler;
import com.example.bloodbank.data.api.ApiServer;
import com.example.bloodbank.data.model.generatedModel;
import com.example.bloodbank.data.model.categories.CategoriesModel;
import com.example.bloodbank.data.model.posts.DataContentPostModel;
import com.example.bloodbank.data.model.posts.PostsModel;

import com.example.bloodbank.helper.OnEndless;

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
import static com.example.bloodbank.helper.HelperMathod.disappearKeypad;

public class ArticlesFragment extends Fragment {

    @BindView(R.id.articlesFragmentShowPostRecyclerView)
    RecyclerView articlesFragmentShowPostRecyclerView;
    ProgressBar articlesFragmentProgBar;
    Unbinder unbinder;
    @BindView(R.id.articlesFragmentSpnCategory)
    Spinner articlesFragmentSpnCategory;
    @BindView(R.id.articles_fragment_Edit_Search)
    EditText articlesFragmentEditSearch;
    @BindView(R.id.articles_fragment_ImvSearch)
    ImageView articlesFragmentImvSearch;

    private ApiServer apiServer;
    private View view;
    private ArrayList<DataContentPostModel> postsArrayList;
    private ArrayList<generatedModel> categoriesArrayList;
    private Integer idCategory = 0;
    private ArticlesAdapterRecycler articlesAdapterRecycler;
    private AdapterSpinner categoryAdapterSpinner;
    private Integer max = 0;
    private OnEndless onEndless;
    private int finalCurrent_page = 0;
    private boolean checkFilterPost = true;
    private SwipeRefreshLayout articlesFragmentShowPostSwipeRefresh;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_articles, container, false);
        unbinder = ButterKnife.bind(this, view);

        disappearKeypad(getActivity(), articlesFragmentEditSearch);

        inti();

        onEndless();

        getDataSpinnerCategory();

        OnClickAllTools();

        SwipeRefresh();

        return view;

    }

    // initialize tools
    private void inti() {
        articlesFragmentProgBar = view.findViewById(R.id.articlesFragmentProgBar);
        articlesFragmentShowPostSwipeRefresh = view.findViewById(R.id.articlesFragmentShowPostSwipeRefresh);
        articlesFragmentProgBar.setVisibility(View.INVISIBLE);

        postsArrayList = new ArrayList<>();
        categoriesArrayList = new ArrayList<>();
        apiServer = getClient().create(ApiServer.class);


    }

    // listener from count items  recyclerView
    private void onEndless() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        articlesFragmentShowPostRecyclerView.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {
                    if (checkFilterPost) {
                        getPosts(current_page);
                    } else {
                        getAllPostFilter(current_page);
                    }
                }
            }
        };

        articlesFragmentShowPostRecyclerView.addOnScrollListener(onEndless);
        articlesAdapterRecycler = new ArticlesAdapterRecycler(postsArrayList, getActivity());
        articlesFragmentShowPostRecyclerView.setAdapter(articlesAdapterRecycler);

        getPosts(1);

    }

    // get all category and add spinner
    private void getDataSpinnerCategory() {

        apiServer.getCategories().enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, final Response<CategoriesModel> response) {

                Log.d(" response call", response.message());

                if (response.body().getStatus() == 1) {

                    // add data default spinner
                    categoriesArrayList.add(new generatedModel(0, "All"));
                    // loop on return data server
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        categoriesArrayList.add(new generatedModel(response.body().getData().get(i).getId(), response.body().getData().get(i).getName()));
                    }

                    // add add return data in array list and add  spinner
                    categoryAdapterSpinner = new AdapterSpinner(getContext(), categoriesArrayList);
                    articlesFragmentSpnCategory.setAdapter(categoryAdapterSpinner);
                    articlesFragmentSpnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            idCategory = categoriesArrayList.get(position).getId();
                       //     Toast.makeText(getContext(), "" + idCategory + "", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else {
                    Toast.makeText(getContext(), "Not PaginationData Category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {

            }
        });
    }

    // this is method all in click
    private void OnClickAllTools() {

        // edit text search keyword
        articlesFragmentImvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (articlesFragmentSpnCategory.getSelectedItemPosition() == 0
                        && articlesFragmentEditSearch.getText().toString().isEmpty() && !checkFilterPost) {

                    articlesAdapterRecycler = new ArticlesAdapterRecycler(postsArrayList, getActivity());
                    articlesFragmentShowPostRecyclerView.setAdapter(articlesAdapterRecycler);
                    checkFilterPost = true;

                } else {

                    articlesAdapterRecycler = new ArticlesAdapterRecycler(postsArrayList, getActivity());
                    articlesFragmentShowPostRecyclerView.setAdapter(articlesAdapterRecycler);
                    checkFilterPost = false;

                    getAllPostFilter(1);

                }

            }
        });
    }

    // get all  post
    private void getPosts(final int pag) {
        try {
            articlesFragmentProgBar.setVisibility(View.VISIBLE);
            // get  PaginationData  post
            apiServer.getPosts(LoadData(getActivity(), USER_API_TOKEN), pag).enqueue(new Callback<PostsModel>() {
                @Override
                public void onResponse(Call<PostsModel> call, Response<PostsModel> response) {
                    Log.d("response BloodT ", response.body().getMsg());
                    articlesFragmentProgBar.setVisibility(View.VISIBLE);

                    if (response.body().getStatus() == 1) {

                        max = response.body().getData().getLastPage();

                        postsArrayList.addAll(response.body().getData().getData());

                        articlesAdapterRecycler.notifyDataSetChanged();

                        articlesFragmentProgBar.setVisibility(View.INVISIBLE);

                    } else {

                        articlesFragmentProgBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getContext(), "Not PaginationData ", Toast.LENGTH_SHORT).show();

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

    // get   Post and Filter = idCategory or KeyWord
    public void getAllPostFilter(final int pag) {
        try {
            String keyword = articlesFragmentEditSearch.getText().toString();
            // get  PaginationData post Filter
            apiServer.getPostsFilter(LoadData(getActivity(), USER_API_TOKEN), pag
                    , keyword, idCategory).enqueue(new Callback<PostsModel>() {
                @Override
                public void onResponse(Call<PostsModel> call, Response<PostsModel> response) {
                    Log.d("response BloodTypes ", response.body().getMsg());

                    articlesFragmentProgBar.setVisibility(View.VISIBLE);
                    if (response.body().getStatus() == 1) {

                        // Clear all data list
                        postsArrayList.clear();
                        articlesAdapterRecycler.notifyDataSetChanged();

                        // add new data
                        postsArrayList.addAll(response.body().getData().getData());
                        articlesAdapterRecycler.notifyDataSetChanged();
                        articlesFragmentProgBar.setVisibility(View.INVISIBLE);

                    } else {
                        Toast.makeText(getContext(), "Not PaginationData", Toast.LENGTH_SHORT).show();
                        articlesFragmentProgBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<PostsModel> call, Throwable t) {
                    articlesFragmentProgBar.setVisibility(View.INVISIBLE);
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

    //  swipeRefresh All list
    private void SwipeRefresh() {

        articlesFragmentShowPostSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postsArrayList.clear();
                getPosts(1);
                articlesFragmentShowPostSwipeRefresh.setRefreshing(true);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                articlesFragmentShowPostSwipeRefresh.setRefreshing(false);

            }
        });
    }
}
