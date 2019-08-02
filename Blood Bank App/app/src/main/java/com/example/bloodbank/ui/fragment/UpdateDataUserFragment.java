package com.example.bloodbank.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.AdapterSpinner;
import com.example.bloodbank.data.api.ApiServer;
import com.example.bloodbank.data.model.generatedModel;
import com.example.bloodbank.data.model.generate.GovernoratesAndBloodTypesModel;
import com.example.bloodbank.data.model.login.Login;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.RetrofitClient.getClient;
import static com.example.bloodbank.data.local.SharedPreferncesManger.Key_password;
import static com.example.bloodbank.data.local.SharedPreferncesManger.LoadData;
import static com.example.bloodbank.data.local.SharedPreferncesManger.USER_API_TOKEN;
import static com.example.bloodbank.helper.HelperMathod.ToolBar;
import static com.example.bloodbank.helper.HelperMathod.checkCorrespondPassword;
import static com.example.bloodbank.helper.HelperMathod.checkLengthPassword;

public class UpdateDataUserFragment extends Fragment {

    @BindView(R.id.updateDataUserFragmentEditUserName)
    EditText updateDataUserFragmentEditUserName;
    @BindView(R.id.updateDataUserFragmentEditEmail)
    EditText updateDataUserFragmentEditEmail;
    @BindView(R.id.updateDataUserFragmentEditDataOfBirth)
    EditText updateDataUserFragmentEditDataOfBirth;
    @BindView(R.id.updateDataUserFragmentBloodTypesSpinner)
    Spinner updateDataUserFragmentBloodTypesSpinner;
    @BindView(R.id.updateDataUserFragmentDataLastDonation)
    EditText updateDataUserFragmentDataLastDonation;
    @BindView(R.id.updateDataUserFragmentGovernoratesSpinner)
    Spinner updateDataUserFragmentGovernoratesSpinner;
    @BindView(R.id.updateDataUserFragmentCiteSpinner)
    Spinner updateDataUserFragmentCiteSpinner;
    @BindView(R.id.updateDataUserFragmentPasswordEdit)
    EditText updateDataUserFragmentPasswordEdit;
    @BindView(R.id.updateDataUserFragmentEmphasisPasswordEdit)
    EditText updateDataUserFragmentEmphasisPasswordEdit;
    @BindView(R.id.updateDataUserFragmentPhoneEdit)
    EditText updateDataUserFragmentPhoneEdit;
    @BindView(R.id.updateDataUserFragmentUpdateDataUserBtn)
    Button updateDataUserFragmentUpdateDataUserBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;

    ProgressBar progressBar;

    private View view;
    ApiServer apiServer;
    private String DataOfBirth, DataLastDonation;
    // value adapter city
    AdapterSpinner adapterSpinner;
    ArrayList<generatedModel> generatedModelArrayListCity;
    generatedModel cityGeneratedModel;
    // value adapter governortare
    AdapterSpinner governortareAdapterSpinner;
    ArrayList<generatedModel> generatedModelArrayListGovernortare;
    generatedModel governorateGeneratedModel;

    AdapterSpinner bloodTypeAdapterSpinner;
    ArrayList<generatedModel> bloodTypeGeneratedModelArrayList;
    generatedModel bloodTypeGeneratedModel;
    private int idCity;
    private int idBloodTypes;
    private int idGovernorates;
    private int YEAR;
    private int MONTH;
    private int DAY_OF_MONTH;
    private Calendar calendar;
    private Integer returnIcCity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_data_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        //
        inti();
        progressBar.setVisibility(View.INVISIBLE);


        OnClick();

        getDataCityGovernorates();

        GetDataProfile();

        return view;
    }

    private void inti() {

        apiServer = getClient().create(ApiServer.class);
        progressBar = view.findViewById(R.id.progressBar);
        generatedModelArrayListGovernortare = new ArrayList<>();
        generatedModelArrayListCity = new ArrayList<>();
        bloodTypeGeneratedModelArrayList = new ArrayList<>();
        // add value tool bar
        ToolBar(getFragmentManager(), getActivity(), toolbar, getResources().getString(R.string.modify_the_data));

    }


    public void getDataCityGovernorates() {

        // get  PaginationData governorates
        apiServer.getGovernorate().enqueue(new Callback<GovernoratesAndBloodTypesModel>() {
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
                        updateDataUserFragmentGovernoratesSpinner.setAdapter(governortareAdapterSpinner);
                        updateDataUserFragmentGovernoratesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                }catch (Exception e){
                    e.getMessage();
                }


            }

            @Override
            public void onFailure(Call<GovernoratesAndBloodTypesModel> call, Throwable t) {

            }
        });

        // get  Blood types
        apiServer.getBlood_types().enqueue(new Callback<GovernoratesAndBloodTypesModel>() {
            @Override
            public void onResponse(Call<GovernoratesAndBloodTypesModel> call, Response<GovernoratesAndBloodTypesModel> response) {

                Log.d("response BloodTypes ", response.body().getMsg());

                if (response.body().getStatus() == 1) {

                    bloodTypeGeneratedModelArrayList.add(new generatedModel(0, "فصيلة الدم"));

                    for (int i = 0; i < response.body().getData().size(); i++) {

                        bloodTypeGeneratedModel = new generatedModel(response.body().getData().get(i).getId(), response.body().getData().get(i).getName());

                        bloodTypeGeneratedModelArrayList.add(bloodTypeGeneratedModel);
                    }

                    bloodTypeAdapterSpinner = new AdapterSpinner(getActivity(), bloodTypeGeneratedModelArrayList);
                    updateDataUserFragmentBloodTypesSpinner.setAdapter(bloodTypeAdapterSpinner);
                    updateDataUserFragmentBloodTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            }

            @Override
            public void onFailure(Call<GovernoratesAndBloodTypesModel> call, Throwable t) {

            }
        });

    }

    // get  PaginationData cities
    private void getDataCity(int idGovernorates) {

        try {
            apiServer.getCities(idGovernorates).enqueue(new Callback<GovernoratesAndBloodTypesModel>() {
                @Override
                public void onResponse(Call<GovernoratesAndBloodTypesModel> call, Response<GovernoratesAndBloodTypesModel> response) {

                    if (response.body().getStatus() == 1) {

                        generatedModelArrayListCity.clear();

                        generatedModelArrayListCity.add(new generatedModel(0, getResources().getString(R.string.city)));

                        for (int i = 0; i < response.body().getData().size(); i++) {

                            cityGeneratedModel = new generatedModel(response.body().getData().get(i).getId(), response.body().getData().get(i).getName());

                            generatedModelArrayListCity.add(cityGeneratedModel);
                        }

                        adapterSpinner = new AdapterSpinner(getContext(), generatedModelArrayListCity);
                        updateDataUserFragmentCiteSpinner.setAdapter(adapterSpinner);
                        updateDataUserFragmentCiteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        updateDataUserFragmentCiteSpinner.setSelection(returnIcCity);
                    }
                }

                @Override
                public void onFailure(Call<GovernoratesAndBloodTypesModel> call, Throwable t) {

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

    @OnClick(R.id.updateDataUserFragmentUpdateDataUserBtn)
    public void onViewClicked() {

        if (checkLengthPassword(updateDataUserFragmentPasswordEdit.getText().toString())
                && checkCorrespondPassword(updateDataUserFragmentPasswordEdit.getText().toString(), updateDataUserFragmentEmphasisPasswordEdit.getText().toString())) {
            Call<Login> call = apiServer.onUpdate(
                    updateDataUserFragmentEditUserName.getText().toString(), updateDataUserFragmentEditEmail.getText().toString(),
                    updateDataUserFragmentEditDataOfBirth.getText().toString(), idCity,
                    updateDataUserFragmentPhoneEdit.getText().toString(), updateDataUserFragmentDataLastDonation.getText().toString(),
                    updateDataUserFragmentPasswordEdit.getText().toString(), updateDataUserFragmentEmphasisPasswordEdit.getText().toString()
                    , idBloodTypes, LoadData(getActivity(), USER_API_TOKEN));
            call.enqueue(new Callback<Login>() {

                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    Log.d("response", response.body().getMsg());
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {

                }
            });
        } else {


            if (!checkLengthPassword(updateDataUserFragmentPasswordEdit.getText().toString())) {
                updateDataUserFragmentEmphasisPasswordEdit.setError(getResources().getString(R.string.It_should_be_the_largest6));
            }
            if (!checkCorrespondPassword(updateDataUserFragmentPasswordEdit.getText().toString(), updateDataUserFragmentEmphasisPasswordEdit.getText().toString())) {
                updateDataUserFragmentEmphasisPasswordEdit.setError(getResources().getString(R.string.number_does_not_correspond));
            }
        }

    }


    public void GetDataProfile() {


        apiServer.getProfile(LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<Login>() {

            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Log.d("response", response.body().getMsg());
                if (response.body().getStatus() == 1) {

                    updateDataUserFragmentEditUserName.setText(response.body().getData().getClient().getName());
                    updateDataUserFragmentEditEmail.setText(response.body().getData().getClient().getEmail());
                    updateDataUserFragmentEditDataOfBirth.setText(response.body().getData().getClient().getBirthDate());
                    updateDataUserFragmentDataLastDonation.setText(response.body().getData().getClient().getDonationLastDate());
                    updateDataUserFragmentPhoneEdit.setText(response.body().getData().getClient().getPhone());
                    updateDataUserFragmentPasswordEdit.setText(LoadData(getActivity(), Key_password));
                    updateDataUserFragmentEmphasisPasswordEdit.setText(LoadData(getActivity(), Key_password));

                    updateDataUserFragmentGovernoratesSpinner.setSelection(Integer.parseInt(response.body().getData().getClient().getCity().getGovernorateId()));

                    updateDataUserFragmentBloodTypesSpinner.setSelection(Integer.parseInt(response.body().getData().getClient().getBloodTypeId()));

                    returnIcCity = response.body().getData().getClient().getCity().getId();

                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    public void OnClick() {

        DecimalFormat decimalFormat = new DecimalFormat("00");
        calendar = Calendar.getInstance();
        YEAR = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.YEAR)));
        MONTH = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.MONTH)));
        DAY_OF_MONTH = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.DAY_OF_MONTH)));

        DataOfBirth = "1972" + "-" + "01" + "-" + "01";
        updateDataUserFragmentEditDataOfBirth.setText(DataOfBirth);
        updateDataUserFragmentEditDataOfBirth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            DataOfBirth = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
                            updateDataUserFragmentEditDataOfBirth.setText(DataOfBirth);
                        }
                    }, YEAR, MONTH, DAY_OF_MONTH);

                    datePickerDialog.show();
                    return true;
                }
                return false;
            }
        });

        updateDataUserFragmentDataLastDonation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            DataLastDonation = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
                            updateDataUserFragmentDataLastDonation.setText(DataLastDonation);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                    datePickerDialog.show();

                    return true;
                }
                return false;
            }
        });

    }


}
