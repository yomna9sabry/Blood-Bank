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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.AdapterSpinner;
import com.example.bloodbank.adapter.DonationRequestsAdapterRecycler;
import com.example.bloodbank.data.api.ApiServer;
import com.example.bloodbank.data.model.generatedModel;
import com.example.bloodbank.data.model.donationRequests.RequestBloodData;
import com.example.bloodbank.data.model.donationRequests.DonationRequestsModel;
import com.example.bloodbank.data.model.generate.GovernoratesAndBloodTypesModel;
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

public class DonationRequestsHomeFragment extends Fragment {
    View view;
    @BindView(R.id.donationRequestsFragmentSearchImgBtn)
    ImageButton donationRequestsFragmentSearchImgBtn;
    @BindView(R.id.donationRequestsFragmentCitySpn)
    Spinner donationRequestsFragmentCitySpn;
    @BindView(R.id.donationRequestsBloodTypeSpn)
    Spinner donationRequestsBloodTypeSpn;
    ProgressBar donationRequestsFragmentProgressBar;
    Unbinder unbinder;
    @BindView(R.id.donationRequestsFragmentShowRequestRecyclerView)
    RecyclerView donationRequestsFragmentShowRequestRecyclerView;

    private ApiServer apiServer;
    private OnEndless onEndless;
    private boolean checkFilterPost = true;
    private int max = 0;
    private DonationRequestsAdapterRecycler donationRequestsAdapterRecycler;
    private ArrayList<RequestBloodData> donationRequestsModleArrayList;
    private ArrayList<generatedModel> generatedModelArrayListGovernortare;
    private generatedModel governorateGeneratedModel;
    private AdapterSpinner adapterSpinner;


    private AdapterSpinner bloodTypeAdapterSpinner;
    private ArrayList<generatedModel> bloodTypeGeneratedModelArrayList;
    private generatedModel bloodTypeGeneratedModel;

    private int idGovernorates;
    private int idBloodTypes;
    private SwipeRefreshLayout donationRequestsFragmentShowRequestSwipeRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_donation_requests, container, false);

        unbinder = ButterKnife.bind(this, view);

        inti();

        getDataGovernorateAndBloodTypeSpinner();

        onEndless();

        OnClickAllTools();

        SwipeRefresh();

        return view;
    }


    // initialize tools
    private void inti() {
        donationRequestsFragmentProgressBar = view.findViewById(R.id.donationRequestsFragmentProgressBar);
        donationRequestsFragmentShowRequestSwipeRefresh = view.findViewById(R.id.donationRequestsFragmentShowRequestSwipeRefresh);

        donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);

        donationRequestsModleArrayList = new ArrayList<>();
        bloodTypeGeneratedModelArrayList = new ArrayList<>();
        generatedModelArrayListGovernortare = new ArrayList<>();
        apiServer = getClient().create(ApiServer.class);


    }

    // listener from count items  recyclerView
    private void onEndless() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        donationRequestsFragmentShowRequestRecyclerView.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {
                    if (checkFilterPost) {
                        getAllDonationRequests(current_page);
                    } else {
                        geDonationRequestsFilter(current_page);
                    }
                }
            }
        };

        donationRequestsFragmentShowRequestRecyclerView.addOnScrollListener(onEndless);
        donationRequestsAdapterRecycler = new DonationRequestsAdapterRecycler(donationRequestsModleArrayList, getActivity());
        donationRequestsFragmentShowRequestRecyclerView.setAdapter(donationRequestsAdapterRecycler);

        getAllDonationRequests(1);

    }

    // get data Governorate And Blood Type Spinner
    private void getDataGovernorateAndBloodTypeSpinner() {


        apiServer.getGovernorate().enqueue(new Callback<GovernoratesAndBloodTypesModel>() {
            @Override
            public void onResponse(Call<GovernoratesAndBloodTypesModel> call, Response<GovernoratesAndBloodTypesModel> response) {

                if (response.body().getStatus() == 1) {
                    generatedModelArrayListGovernortare.add(new generatedModel(0, "المحافظه"));

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        governorateGeneratedModel = new generatedModel(response.body().getData().get(i).getId(),
                                response.body().getData().get(i).getName());
                        generatedModelArrayListGovernortare.add(governorateGeneratedModel);
                    }

                    adapterSpinner = new AdapterSpinner(getContext(), generatedModelArrayListGovernortare);
                    donationRequestsFragmentCitySpn.setAdapter(adapterSpinner);
                    donationRequestsFragmentCitySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            idGovernorates = generatedModelArrayListGovernortare.get(position).getId();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GovernoratesAndBloodTypesModel> call, Throwable t) {

            }
        });

        Call<GovernoratesAndBloodTypesModel> getBlood_types = apiServer.getBlood_types();
        getBlood_types.enqueue(new Callback<GovernoratesAndBloodTypesModel>() {
            @Override
            public void onResponse(Call<GovernoratesAndBloodTypesModel> call, Response<GovernoratesAndBloodTypesModel> response) {
                Log.d("response BloodTypes ", response.body().getMsg());

                if (response.body().getStatus() == 1) {

                    bloodTypeGeneratedModelArrayList.add(new generatedModel(0, "فصيلة الدم"));

                    for (int i = 0; i < response.body().getData().size(); i++) {

                        bloodTypeGeneratedModel = new generatedModel(response.body().getData().get(i).getId(), response.body().getData().get(i).getName());

                        bloodTypeGeneratedModelArrayList.add(bloodTypeGeneratedModel);
                    }

                    bloodTypeAdapterSpinner = new AdapterSpinner(getContext(), bloodTypeGeneratedModelArrayList);
                    donationRequestsBloodTypeSpn.setAdapter(bloodTypeAdapterSpinner);
                    donationRequestsBloodTypeSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            idBloodTypes = bloodTypeGeneratedModelArrayList.get(position).getId();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GovernoratesAndBloodTypesModel> call, Throwable t) {

            }
        });
    }

    //  Donation Requests Filter
    private void geDonationRequestsFilter(int current_page) {

        donationRequestsFragmentProgressBar.setVisibility(View.VISIBLE);

        apiServer.getDonationRequestsFilter(LoadData(getActivity(), USER_API_TOKEN),   idBloodTypes, idGovernorates,current_page).enqueue(new Callback<DonationRequestsModel>() {
            @Override
            public void onResponse(Call<DonationRequestsModel> call, Response<DonationRequestsModel> response) {
                try {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus() == 1) {

                        if (response.body().getData().getTotal() == 0) {
                            Toast.makeText(getContext(), "Not Pagination DataNotifyPage", Toast.LENGTH_SHORT).show();
                        }
                        // Clear all data list
                        donationRequestsModleArrayList.clear();
                        // notify DataNotifyPage Set Changed
                        donationRequestsAdapterRecycler.notifyDataSetChanged();
                        // add All
                        donationRequestsModleArrayList.addAll(response.body().getData().getRequestBloodData());
                        // notify DataNotifyPage Set Changed
                        donationRequestsAdapterRecycler.notifyDataSetChanged();
                        //  set Visibility INVISIBLE
                        donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);

                    } else {

                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);

                    }
                } catch (Exception e) {

                    e.getMessage();
                }


            }

            @Override
            public void onFailure(Call<DonationRequestsModel> call, Throwable t) {
                donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

    //  All Donation Requests
    private void getAllDonationRequests(int current_page) {

        donationRequestsFragmentProgressBar.setVisibility(View.VISIBLE);

        apiServer.getDonationRequests(LoadData(getActivity(), USER_API_TOKEN), current_page).enqueue(new Callback<DonationRequestsModel>() {
            @Override
            public void onResponse(Call<DonationRequestsModel> call, Response<DonationRequestsModel> response) {

                try {
                    if (response.body().getStatus() == 1) {

                        if (response.body().getData().getTotal() == 0) {
                            Toast.makeText(getContext(), "Not Pagination DataNotifyPage", Toast.LENGTH_SHORT).show();
                        }
                        max = response.body().getData().getLastPage();

                        donationRequestsModleArrayList.addAll(response.body().getData().getRequestBloodData());

                        donationRequestsAdapterRecycler.notifyDataSetChanged();

                        donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(getContext(), "Not Pagination DataNotifyPage", Toast.LENGTH_SHORT).show();
                        donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);
                    }

                }catch (Exception e){
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<DonationRequestsModel> call, Throwable t) {
                donationRequestsFragmentProgressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

    // this is method all in click
    private void OnClickAllTools() {
        // edit text search keyword
        donationRequestsFragmentSearchImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (donationRequestsFragmentCitySpn.getSelectedItemPosition() ==
                        0 && donationRequestsBloodTypeSpn.getSelectedItemPosition() == 0 && !checkFilterPost) {

                    donationRequestsAdapterRecycler = new DonationRequestsAdapterRecycler(donationRequestsModleArrayList, getActivity());
                    donationRequestsFragmentShowRequestRecyclerView.setAdapter(donationRequestsAdapterRecycler);
                    checkFilterPost = true;

                } else {

                    donationRequestsAdapterRecycler = new DonationRequestsAdapterRecycler(donationRequestsModleArrayList, getActivity());
                    donationRequestsFragmentShowRequestRecyclerView.setAdapter(donationRequestsAdapterRecycler);
                    checkFilterPost = false;

                    geDonationRequestsFilter(1);

                }

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //  swipeRefresh All list
    private void SwipeRefresh() {

        donationRequestsFragmentShowRequestSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllDonationRequests(1);

                donationRequestsFragmentShowRequestSwipeRefresh.setRefreshing(true);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                donationRequestsFragmentShowRequestSwipeRefresh.setRefreshing(false);

            }
        });
    }
}
