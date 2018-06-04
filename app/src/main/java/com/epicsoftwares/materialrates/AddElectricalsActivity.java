package com.epicsoftwares.materialrates;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class AddElectricalsActivity extends AppCompatActivity {

    private TextInputLayout layoutInputMatName;
    private TextInputLayout layoutInputType;
    private TextInputLayout layoutSpinnerDimen;

    private AppCompatAutoCompleteTextView inputMatName;
    private AppCompatAutoCompleteTextView inputType;
    private Spinner spinnerDimen;
    private AppCompatButton btnAdd;

    private ArrayAdapter<String> inputMatNameAdapter;
    private ArrayAdapter<String> inputTypeAdapter;
    private ArrayAdapter<String> spinnerAdapter;

    private ArrayList<String> inputMatNameItems;
    private ArrayList<String> inputTypeItems;
    private ArrayList<String> spinnerItems;

    private AppDatabase appDatabase;

    private Electricals electricals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_electricals);

        getSupportActionBar().setTitle("Add Electrical Materials");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appDatabase = AppDatabase.getDB(this);

        inputMatNameItems = new ArrayList<>();
        inputTypeItems = new ArrayList<>();
        spinnerItems = new ArrayList<>();

        inputMatNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, inputMatNameItems);
        inputTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, inputTypeItems);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutInputMatName = findViewById(R.id.layout_input_mat_name_electricals);
        layoutInputType = findViewById(R.id.layout_input_type_electricals);
        inputMatName = findViewById(R.id.input_mat_name_electricals);
        inputType = findViewById(R.id.input_type_electricals);
        spinnerDimen = findViewById(R.id.spinner_dimen_electricals);
        btnAdd = findViewById(R.id.btn_add_electricals);

        btnAdd.setEnabled(false);
        spinnerDimen.setAdapter(spinnerAdapter);

        spinnerDimen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputMatName.addTextChangedListener(new MyTextWatcher(inputMatName));
        inputType.addTextChangedListener(new MyTextWatcher(inputType));

        inputMatName.setThreshold(1);
        inputType.setThreshold(1);
        inputMatName.setAdapter(inputMatNameAdapter);
        inputType.setAdapter(inputTypeAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Electricals electricals1 = null;
                electricals = null;

                electricals.setMaterialName(inputMatName.getText().toString().toUpperCase());
                electricals.setType(inputType.getText().toString().toUpperCase());
                electricals.setMaterialID(generateMatId());

                appDatabase.electricalsDAO().insertElectricals(electricals);
                electricals1 = appDatabase.electricalsDAO().getElectricals(electricals.getMaterialID());
                if ( electricals1 == null )
                    Toast.makeText(getApplicationContext(), "Electricals Added!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Electricals not Added", Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(), "No. of Electricals: " + appDatabase.electricalsDAO().getNoOfElectricalssMaterials(), Toast.LENGTH_SHORT).show();

            }
        });

        inputMatNameItems.addAll(appDatabase.electricalsDAO().getAllMatNames());
        inputTypeItems.addAll(appDatabase.electricalsDAO().getAllTypes());
        inputMatNameAdapter.notifyDataSetChanged();
        inputTypeAdapter.notifyDataSetChanged();

    }//onCreate;

    private String generateMatId() {
        Random rand = new Random();
        String dimenid = "19";
        StringBuffer matID = new StringBuffer();
        ArrayList<String> dimensID = new ArrayList<>();
        dimensID.addAll(appDatabase.electricalsDAO().getAllDimensID());

        matID.append("EL");
        matID.append(inputMatName.getText().toString().substring(0, 1));
        while ( dimensID.contains(dimenid) && dimensID.size() > 0) {
            dimenid = String.valueOf(rand.nextInt(100));
            electricals.setDimenID(dimenid);
        }//while;
        matID.append(dimenid);
        matID.append(inputType.getText().toString().substring(0,1));

        Toast.makeText(this, matID.toString(), Toast.LENGTH_SHORT).show();

        return matID.toString();
    }//generateMatId;

    private class MyTextWatcher implements TextWatcher {

        private View v;

        public MyTextWatcher(View v){
            this.v = v;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            switch ( v.getId() ) {
                case R.id.input_mat_name_electricals:
                    validateData();
                    break;
                case R.id.input_type_electricals:
                    validateData();
                    break;
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

        void validateData() {
            if ( !inputMatName.getText().toString().isEmpty() &&
                    !inputType.getText().toString().isEmpty() &&
                    spinnerDimen.isSelected() ) {
                btnAdd.setEnabled(true);
            } else {
                btnAdd.setEnabled(false);
            }
        }//validateData;
    }//MyTextWatcher;


    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }//onDestroy;
}//AddElectricalsActivity;
