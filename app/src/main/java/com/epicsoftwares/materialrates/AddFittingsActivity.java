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

public class AddFittingsActivity extends AppCompatActivity {

    private TextInputLayout layoutInputMatName;
    private TextInputLayout layoutInputType;
    private TextInputLayout layoutSpinnerDimen;

    private AppCompatAutoCompleteTextView inputMatName;
    private AppCompatAutoCompleteTextView inputType;
    private Spinner spinnerDimen;
    private AppCompatButton btnAdd;

    private ArrayAdapter<String> spinnerDimenAdapter;
    private ArrayAdapter<String> inputMatNameAdapter;
    private ArrayAdapter<String> inputTypeAdapter;

    private ArrayList<String> inputMatNameItems;
    private ArrayList<String> inputTypeItems;
    private ArrayList<String> spinnerItems;

    private AppDatabase appDatabase;

    private Fittings fittings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fittings);

        getSupportActionBar().setTitle("Add Fittings Material");

        appDatabase = AppDatabase.getDB(this);

        spinnerItems = new ArrayList<>();
        inputMatNameItems = new ArrayList<>();
        inputTypeItems = new ArrayList<>();

        spinnerDimenAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        inputMatNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, inputMatNameItems);
        inputTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, inputTypeItems);

        layoutInputMatName = findViewById(R.id.layout_input_mat_name_fittings);
        layoutInputType = findViewById(R.id.layout_input_type_fittings);
        spinnerDimen = findViewById(R.id.spinner_dimen_fittings);
        inputMatName = findViewById(R.id.input_mat_name_fittings);
        inputType = findViewById(R.id.input_type_fittings);
        btnAdd = findViewById(R.id.btn_add_fittings);
        layoutSpinnerDimen = findViewById(R.id.layout_spinner_dimen_fittings);

        spinnerDimenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btnAdd.setEnabled(false);
        spinnerDimen.setAdapter(spinnerDimenAdapter);

        spinnerDimen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fittings.setDimen(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputMatName.addTextChangedListener(new MyTextWatcher(inputMatName));
        inputType.addTextChangedListener(new MyTextWatcher(inputType));

        inputType.setThreshold(1);
        inputMatName.setThreshold(1);
        inputMatName.setAdapter(inputMatNameAdapter);
        inputType.setAdapter(inputTypeAdapter);

        spinnerItems.addAll(appDatabase.fittingsDAO().getAllDimens());

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fittings fittings1 = null;

                fittings.setMaterialName(inputMatName.getText().toString().toUpperCase());
                fittings.setType(inputType.getText().toString().toUpperCase());
                fittings.setMaterialID(generateMatId());

                appDatabase.fittingsDAO().insertFittings(fittings);
                fittings1 = appDatabase.fittingsDAO().getFittings(fittings.getMaterialID());
            }
        });

        inputMatNameItems.addAll(appDatabase.fittingsDAO().getAllMatNames());
        inputTypeItems.addAll(appDatabase.fittingsDAO().getAllTypes());
        inputMatNameAdapter.notifyDataSetChanged();
        inputTypeAdapter.notifyDataSetChanged();


    }//onCreate;

    private String generateMatId() {
        Random rand = new Random();
        String dimenid = "19";
        StringBuffer matID = new StringBuffer();
        ArrayList<String> dimensID = new ArrayList<>();
        dimensID.addAll(appDatabase.fittingsDAO().getAllDimensID());

        matID.append("FI");
        matID.append(inputMatName.getText().toString().substring(0, 1));
        while ( dimensID.contains(dimenid) && dimensID.size() > 0) {
            dimenid = String.valueOf(rand.nextInt(100));
            fittings.setDimenID(dimenid);
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
                case R.id.input_mat_name_fittings:
                    validateData();
                    break;
                case R.id.input_type_fittings:
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

}//AddFittingsActivity;

