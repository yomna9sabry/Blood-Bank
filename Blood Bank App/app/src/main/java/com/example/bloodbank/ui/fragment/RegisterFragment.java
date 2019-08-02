package com.example.bloodbank.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.bloodbank.ui.activity.HomeNavgation;

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
import static com.example.bloodbank.helper.HelperMathod.checkCorrespondPassword;
import static com.example.bloodbank.helper.HelperMathod.checkLengthPassword;

public class RegisterFragment extends Fragment {


    Unbinder unbinder;

    @BindView(R.id.registerFragmentEditUserName)
    EditText registerFragmentEditUserName;
    @BindView(R.id.registerFragmentEditEmail)
    EditText registerFragmentEditEmail;
    @BindView(R.id.registerFragmentEditDataOfBirth)
    EditText registerFragmentEditDataOfBirth;
    @BindView(R.id.registerFragmentBloodTypesSpinner)
    Spinner registerFragmentBloodTypesSpinner;
    @BindView(R.id.registerFragmentDataLastDonation)
    EditText registerFragmentDataLastDonation;
    @BindView(R.id.registerFragmentGovernoratesSpinner)
    Spinner registerFragmentGovernoratesSpinner;
    @BindView(R.id.registerFragmentCiteSpinner)
    Spinner registerFragmentCiteSpinner;
    @BindView(R.id.registerFragmentPasswordEdit)
    EditText registerFragmentPasswordEdit;
    @BindView(R.id.registerFragmentEmphasisPasswordEdit)
    EditText registerFragmentEmphasisPasswordEdit;
    @BindView(R.id.registerFragmentPhoneEdit)
    EditText registerFragmentPhoneEdit;
    @BindView(R.id.registerFragmentRegisterBtn)
    Button registerFragmentRegisterBtn;
    @BindView(R.id.redisterFragmentTolBr)
    Toolbar redisterFragmentTolBr;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        //
        inti();
        unbinder = ButterKnife.bind(this, view);


        OnClick();

        getDataCityGovernorates();


        return view;
    }

    public void getDataCityGovernorates() {
        progressBar.setVisibility(View.VISIBLE);

        // get  PaginationData governorates
        Call<GovernoratesAndBloodTypesModel> GovernoratesCall = apiServer.getGovernorate();
        GovernoratesCall.enqueue(new Callback<GovernoratesAndBloodTypesModel>() {
            @Override
            public void onResponse(Call<GovernoratesAndBloodTypesModel> call, Response<GovernoratesAndBloodTypesModel> response) {
                Log.d("response", response.body().getMsg());

                if (response.body().getStatus() == 1) {

                    generatedModelArrayListGovernortare.add(new generatedModel(0, "المحافظه"));

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        governorateGeneratedModel = new generatedModel(response.body().getData().get(i).getId(),
                                response.body().getData().get(i).getName());
                        generatedModelArrayListGovernortare.add(governorateGeneratedModel);
                    }

                    governortareAdapterSpinner = new AdapterSpinner(getContext(), generatedModelArrayListGovernortare);
                    registerFragmentGovernoratesSpinner.setAdapter(governortareAdapterSpinner);
                    registerFragmentGovernoratesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                } else {

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

                if (response.body().getStatus() == 1) {

                    bloodTypeGeneratedModelArrayList.add(new generatedModel(0, "فصيلة الدم"));

                    for (int i = 0; i < response.body().getData().size(); i++) {

                        bloodTypeGeneratedModel = new generatedModel(response.body().getData().get(i).getId(), response.body().getData().get(i).getName());

                        bloodTypeGeneratedModelArrayList.add(bloodTypeGeneratedModel);
                    }

                    bloodTypeAdapterSpinner = new AdapterSpinner(getContext(), bloodTypeGeneratedModelArrayList);
                    registerFragmentBloodTypesSpinner.setAdapter(bloodTypeAdapterSpinner);
                    registerFragmentBloodTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    registerFragmentCiteSpinner.setAdapter(adapterSpinner);
                    registerFragmentCiteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void inti() {
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        apiServer = getClient().create(ApiServer.class);
        generatedModelArrayListGovernortare = new ArrayList<>();
        generatedModelArrayListCity = new ArrayList<>();
        bloodTypeGeneratedModelArrayList = new ArrayList<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.registerFragmentRegisterBtn)
    public void onViewClicked() {

        if (checkLengthPassword(registerFragmentPasswordEdit.getText().toString())
                && checkCorrespondPassword(registerFragmentPasswordEdit.getText().toString(), registerFragmentEmphasisPasswordEdit.getText().toString())) {
            Call<Login> call = apiServer.onRegister(
                    registerFragmentEditUserName.getText().toString(), registerFragmentEditEmail.getText().toString(),
                    registerFragmentEditDataOfBirth.getText().toString(), idCity,
                    registerFragmentPhoneEdit.getText().toString(), registerFragmentDataLastDonation.getText().toString(),
                    registerFragmentPasswordEdit.getText().toString(), registerFragmentEmphasisPasswordEdit.getText().toString()
                    , idBloodTypes);
            call.enqueue(new Callback<Login>() {

                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    progressBar.setVisibility(View.VISIBLE);

                    Log.d("response", response.body().getMsg());

                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus() == 1) {

                        progressBar.setVisibility(View.VISIBLE);

                        Intent intent = new Intent(getContext(), HomeNavgation.class);
                        startActivity(intent);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);

                    }

                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {

                }
            });
        } else {


            if (!checkLengthPassword(registerFragmentPasswordEdit.getText().toString())) {
                registerFragmentEmphasisPasswordEdit.setError(getResources().getString(R.string.It_should_be_the_largest6));
            }
            if (!checkCorrespondPassword(registerFragmentPasswordEdit.getText().toString(), registerFragmentEmphasisPasswordEdit.getText().toString())) {
                registerFragmentEmphasisPasswordEdit.setError(getResources().getString(R.string.number_does_not_correspond));
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    public void OnClick() {

        DecimalFormat decimalFormat = new DecimalFormat("00");
        calendar = Calendar.getInstance();
        YEAR = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.YEAR)));
        MONTH = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.MONTH)));
        DAY_OF_MONTH = Integer.parseInt(decimalFormat.format(calendar.get(Calendar.DAY_OF_MONTH)));

        DataOfBirth = "1972" + "-" + "01" + "-" + "01";
        registerFragmentEditDataOfBirth.setText(DataOfBirth);
        registerFragmentEditDataOfBirth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            DataOfBirth = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
                            registerFragmentEditDataOfBirth.setText(DataOfBirth);
                        }
                    }, YEAR, MONTH, DAY_OF_MONTH);

                    datePickerDialog.show();
                    return true;
                }
                return false;
            }
        });

        registerFragmentDataLastDonation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("DefaultLocale")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            DataLastDonation = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
                            registerFragmentDataLastDonation.setText(DataLastDonation);
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
