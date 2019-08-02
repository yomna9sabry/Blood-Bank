package com.example.bloodbank.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.AdapterSpinner;
import com.example.bloodbank.data.api.ApiServer;
import com.example.bloodbank.data.model.donationRequests.DonationRequestsModel;
import com.example.bloodbank.data.model.generate.GovernoratesAndBloodTypesModel;
import com.example.bloodbank.data.model.generatedModel;
import com.example.bloodbank.ui.activity.MapsActivity;

import java.util.ArrayList;

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
import static com.example.bloodbank.helper.HelperMathod.ToolBar;
import static com.example.bloodbank.ui.activity.MapsActivity.latitude;
import static com.example.bloodbank.ui.activity.MapsActivity.longitude;

public class AddDonationRequestFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.addDonationRequestFragmentEditUserName)
    EditText addDonationRequestFragmentEditUserName;
    @BindView(R.id.addDonationRequestFragmentEditAge)
    EditText addDonationRequestFragmentEditAge;
    @BindView(R.id.addDonationRequestFragmentBloodTypesSpinner)
    Spinner addDonationRequestFragmentBloodTypesSpinner;
    @BindView(R.id.addDonationRequestFragmentEditNumberBag)
    Spinner addDonationRequestFragmentEditNumberBag;
    @BindView(R.id.addDonationRequestFragmentNameHospitalEdit)
    EditText addDonationRequestFragmentNameHospitalEdit;
    @BindView(R.id.addDonationRequestFragmentAddressHospitalEdit)
    EditText addDonationRequestFragmentAddressHospitalEdit;
    @BindView(R.id.addDonationRequestFragmentGovernoratesSpinner)
    Spinner addDonationRequestFragmentGovernoratesSpinner;
    @BindView(R.id.addDonationRequestFragmentCiteSpinner)
    Spinner addDonationRequestFragmentCiteSpinner;
    @BindView(R.id.addDonationRequestFragmentPhoneEdit)
    EditText addDonationRequestFragmentPhoneEdit;
    @BindView(R.id.addDonationRequestFragmentNoteEdit)
    EditText addDonationRequestFragmentNoteEdit;
    @BindView(R.id.addDonationFragmentSendBtn)
    Button addDonationFragmentSendBtn;

    @BindView(R.id.addDonationRequestLocationMapImg)
    ImageView addDonationRequestLocationMapImg;
    ProgressBar addDonationRequestprogressBar;


    private View view;
    private ApiServer apiServer;
    // value adapter city
    private AdapterSpinner adapterSpinner;
    private ArrayList<generatedModel> generatedModelArrayListCity;
    private generatedModel cityGeneratedModel;
    // value adapter governortare
    private AdapterSpinner governortareAdapterSpinner;
    private ArrayList<generatedModel> generatedModelArrayListGovernortare;
    private generatedModel governorateGeneratedModel;
    // value adapter bloodType
    private AdapterSpinner bloodTypeAdapterSpinner;
    private ArrayList<generatedModel> bloodTypeGeneratedModelArrayList;
    private generatedModel bloodTypeGeneratedModel;
    // value adapter Number Bag
    private AdapterSpinner numberBagAdapterSpinner;
    private ArrayList<generatedModel> numberBagGeneratedModelArrayList;
    private generatedModel numberBagGeneratedModel;

    private int idCity;
    private int idBloodTypes;
    private int idGovernorates;

    private int numBagBlood;
    private String EditUserName, EditAge, NameHospitalEdit, AddressHospitalEdit, PhoneEdit, NoteEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_donation_request, container, false);

        unbinder = ButterKnife.bind(this, view);

        // initializer tools
        inti();

        //get DataNotifyPage City and Governorates
        getDataCityGovernorates();
        // add spinner number bag blood
        addNumberBagBlood();

        addDonationRequestprogressBar.setVisibility(View.INVISIBLE);


        return view;
    }

    // initializer tools
    private void inti() {
        addDonationRequestprogressBar = view.findViewById(R.id.addDonationRequestprogressBar);

        apiServer = getClient().create(ApiServer.class);
        generatedModelArrayListGovernortare = new ArrayList<>();
        generatedModelArrayListCity = new ArrayList<>();
        bloodTypeGeneratedModelArrayList = new ArrayList<>();
        numberBagGeneratedModelArrayList = new ArrayList<>();


        // add value tool bar
        ToolBar(getFragmentManager(), getActivity(), toolbar, getResources().getString(R.string.donation_request));
    }


    //get DataNotifyPage City and Governorates
    public void getDataCityGovernorates() {

        // get  PaginationData governorates
        Call<GovernoratesAndBloodTypesModel> GovernoratesCall = apiServer.getGovernorate();
        GovernoratesCall.enqueue(new Callback<GovernoratesAndBloodTypesModel>() {
            @Override
            public void onResponse(Call<GovernoratesAndBloodTypesModel> call, Response<GovernoratesAndBloodTypesModel> response) {
                Log.d("response", response.body().getMsg());
                try {
                    if (response.body().getStatus() == 1) {

                        generatedModelArrayListGovernortare.add(new generatedModel(0, "المحافظه"));

                        for (int i = 0; i < response.body().getData().size(); i++) {
                            governorateGeneratedModel = new generatedModel(response.body().getData().get(i).getId(),
                                    response.body().getData().get(i).getName());
                            generatedModelArrayListGovernortare.add(governorateGeneratedModel);
                        }

                        governortareAdapterSpinner = new AdapterSpinner(getContext(), generatedModelArrayListGovernortare);
                        addDonationRequestFragmentGovernoratesSpinner.setAdapter(governortareAdapterSpinner);
                        addDonationRequestFragmentGovernoratesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    idGovernorates = bloodTypeGeneratedModelArrayList.get(position).getId();
                                    // get  PaginationData cities
                                    getDataCity(idGovernorates);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<GovernoratesAndBloodTypesModel> call, Throwable t) {

            }
        });

        // get  Blood types
        Call<GovernoratesAndBloodTypesModel> getBlood_types = apiServer.getBlood_types();
        getBlood_types.enqueue(new Callback<GovernoratesAndBloodTypesModel>() {
            @Override
            public void onResponse(Call<GovernoratesAndBloodTypesModel> call, Response<GovernoratesAndBloodTypesModel> response) {
                Log.d("response BloodTypes ", response.body().getMsg());
                try {
                    if (response.body().getStatus() == 1) {

                        bloodTypeGeneratedModelArrayList.add(new generatedModel(0, "فصيلة الدم"));

                        for (int i = 0; i < response.body().getData().size(); i++) {

                            bloodTypeGeneratedModel = new generatedModel(response.body().getData().get(i).getId(), response.body().getData().get(i).getName());

                            bloodTypeGeneratedModelArrayList.add(bloodTypeGeneratedModel);
                        }

                        bloodTypeAdapterSpinner = new AdapterSpinner(getContext(), bloodTypeGeneratedModelArrayList);
                        addDonationRequestFragmentBloodTypesSpinner.setAdapter(bloodTypeAdapterSpinner);
                        addDonationRequestFragmentBloodTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {

                                    idBloodTypes = bloodTypeGeneratedModelArrayList.get(position).getId();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }catch (Exception e){
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<GovernoratesAndBloodTypesModel> call, Throwable t) {

            }
        });

    }

    // get  data cities
    private void getDataCity(int idGovernorates) {


        Call<GovernoratesAndBloodTypesModel> citiesCall = apiServer.getCities(idGovernorates);
        citiesCall.enqueue(new Callback<GovernoratesAndBloodTypesModel>() {
            @Override
            public void onResponse(Call<GovernoratesAndBloodTypesModel> call, Response<GovernoratesAndBloodTypesModel> response) {

                if (response.body().getStatus() == 1) {
                    generatedModelArrayListCity.clear();
                    generatedModelArrayListCity.add(new generatedModel(0,
                            getResources().getString(R.string.city)));

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        cityGeneratedModel = new generatedModel(response.body().getData().get(i).getId(),
                                response.body().getData().get(i).getName());

                        generatedModelArrayListCity.add(cityGeneratedModel);
                    }

                    adapterSpinner = new AdapterSpinner(getContext(), generatedModelArrayListCity);
                    addDonationRequestFragmentCiteSpinner.setAdapter(adapterSpinner);
                    addDonationRequestFragmentCiteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                idCity = bloodTypeGeneratedModelArrayList.get(position).getId();
                            }
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

    // add spinner number bag blood
    private void addNumberBagBlood() {
        numberBagGeneratedModelArrayList.add(new generatedModel(0, "Number Bag"));
        numberBagGeneratedModelArrayList.add(new generatedModel(1, "1"));
        numberBagGeneratedModelArrayList.add(new generatedModel(2, "2"));
        numberBagGeneratedModelArrayList.add(new generatedModel(3, "3"));
        numberBagGeneratedModelArrayList.add(new generatedModel(4, "4"));
        numberBagGeneratedModelArrayList.add(new generatedModel(5, "5"));
        numberBagGeneratedModelArrayList.add(new generatedModel(6, "6"));
        numberBagGeneratedModelArrayList.add(new generatedModel(7, "7"));
        numberBagGeneratedModelArrayList.add(new generatedModel(8, "8"));
        numberBagGeneratedModelArrayList.add(new generatedModel(9, "9"));
        numberBagGeneratedModelArrayList.add(new generatedModel(10, "10"));


        numberBagAdapterSpinner = new AdapterSpinner(getContext(), numberBagGeneratedModelArrayList);
        addDonationRequestFragmentEditNumberBag.setAdapter(numberBagAdapterSpinner);
        addDonationRequestFragmentEditNumberBag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    numBagBlood = numberBagGeneratedModelArrayList.get(position).getId();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //  All Donation Requests
    private void SendDonationRequests() {
        addDonationRequestprogressBar.setVisibility(View.VISIBLE);

        addDonationRequestprogressBar.setVisibility(View.VISIBLE);
        apiServer.CreateDonationRequests(LoadData(getActivity(), USER_API_TOKEN), EditUserName, EditAge, idBloodTypes, numBagBlood, NameHospitalEdit
                , AddressHospitalEdit, idCity, PhoneEdit, NoteEdit, latitude, longitude).enqueue(new Callback<DonationRequestsModel>() {
            @Override
            public void onResponse(Call<DonationRequestsModel> call, Response<DonationRequestsModel> response) {
                addDonationRequestprogressBar.setVisibility(View.VISIBLE);

                if (response.body().getStatus() == 1) {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    addDonationRequestprogressBar.setVisibility(View.INVISIBLE);
                    clearText();
                } else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    addDonationRequestprogressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<DonationRequestsModel> call, Throwable t) {
                addDonationRequestprogressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

    private void clearText() {
        addDonationRequestFragmentEditUserName.getText().clear();
        addDonationRequestFragmentEditAge.getText().clear();
        addDonationRequestFragmentNameHospitalEdit.getText().clear();
        addDonationRequestFragmentAddressHospitalEdit.getText().clear();
        addDonationRequestFragmentPhoneEdit.getText().clear();
        addDonationRequestFragmentNoteEdit.getText().clear();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.addDonationFragmentSendBtn)
    public void onViewClicked() {

        getDataEdit();

        SendDonationRequests();
    }

    private void getDataEdit() {
        // get data from ediText
        EditUserName = addDonationRequestFragmentEditUserName.getText().toString();
        EditAge = addDonationRequestFragmentEditAge.getText().toString();
        NameHospitalEdit = addDonationRequestFragmentNameHospitalEdit.getText().toString();
        AddressHospitalEdit = addDonationRequestFragmentAddressHospitalEdit.getText().toString();
        PhoneEdit = addDonationRequestFragmentPhoneEdit.getText().toString();
        NoteEdit = addDonationRequestFragmentNoteEdit.getText().toString();
    }

    @OnClick(R.id.addDonationRequestLocationMapImg)
    public void onViewClickedLocation() {

        startActivity(new Intent(getActivity(), MapsActivity.class));
    }
}
