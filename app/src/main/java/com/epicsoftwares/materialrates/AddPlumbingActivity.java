package com.epicsoftwares.materialrates;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class AddPlumbingActivity extends AppCompatActivity {

    private TextInputLayout layoutInputMaterialName;
    private TextInputLayout layoutInputType;
    private TextInputLayout layoutInputDimen;

    private AppCompatButton btnAdd;
    private AppCompatAutoCompleteTextView inputMaterialName;
    private AppCompatAutoCompleteTextView inputType;
    private AppCompatAutoCompleteTextView inputDimen;

    private ArrayAdapter<String> inputDimenAdapter;
    private ArrayAdapter<String> inputMaterialNameAdapter;
    private ArrayAdapter<String> inputTypeAdapter;

    private ArrayList<String> dimenItems;
    private ArrayList<String> inputMaterialItems;
    private ArrayList<String> inputTypeItems;

    private AppDatabase appDatabase;

    private Plumbing plumbing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plumbing);

        plumbing = new Plumbing();

        getSupportActionBar().setTitle("Add Plumbing Materials");

        appDatabase = AppDatabase.getDB(this);

        dimenItems = new ArrayList<>();
        inputMaterialItems = new ArrayList<>();
        inputTypeItems = new ArrayList<>();

        inputDimenAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dimenItems);
        inputMaterialNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, inputMaterialItems);
        inputTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, inputTypeItems);

        layoutInputMaterialName = findViewById(R.id.layout_input_mat_name_plumbing);
        layoutInputType = findViewById(R.id.layout_input_type_plumbing);
        layoutInputDimen = findViewById(R.id.layout_input_dimen_plumbing);
        inputDimen = findViewById(R.id.input_dimen_plumbing);
        inputMaterialName = findViewById(R.id.input_mat_name_plumbing);
        inputType = findViewById(R.id.input_type_plumbing);
        btnAdd = findViewById(R.id.btn_add_plumbing);

        btnAdd.setEnabled(false);

        inputMaterialName.addTextChangedListener(new MyTextWatcher(inputMaterialName));
        inputType.addTextChangedListener(new MyTextWatcher(inputType));
        inputDimen.addTextChangedListener(new MyTextWatcher(inputDimen));

        inputMaterialName.setThreshold(1);
        inputType.setThreshold(1);
        inputDimen.setThreshold(1);
        inputMaterialName.setAdapter(inputMaterialNameAdapter);
        inputType.setAdapter(inputTypeAdapter);
        inputDimen.setAdapter(inputDimenAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Plumbing plumbing1 = new Plumbing();

                plumbing.setMaterialName(inputMaterialName.getText().toString().toUpperCase());
                plumbing.setType(inputType.getText().toString().toUpperCase());
                plumbing.setMaterialID(generateMatId());
                plumbing.setDimen(inputDimen.getText().toString());

                appDatabase.plumbingDAO().insertPlumbing(plumbing);
                plumbing1 = appDatabase.plumbingDAO().getPlumbing(plumbing.getMaterialID());
                if ( plumbing1 == null )
                    Toast.makeText(getApplicationContext(),"Material not Added.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Material Added Successfully!", Toast.LENGTH_SHORT).show();

                Toast.makeText(getApplicationContext(), "No. of Materials: " + appDatabase.plumbingDAO().getNoOfPlumbingMaterials(), Toast.LENGTH_SHORT).show();
            }
        });

        inputMaterialItems.addAll(appDatabase.plumbingDAO().getAllMatNames());
        inputTypeItems.addAll(appDatabase.plumbingDAO().getAllTypes());
        dimenItems.addAll(appDatabase.plumbingDAO().getAllDimens());
        inputMaterialNameAdapter.notifyDataSetChanged();
        inputTypeAdapter.notifyDataSetChanged();
        inputDimenAdapter.notifyDataSetChanged();

    }//onCreate;

    private String generateMatId() {
        Random rand = new Random();
        String dimenid = "19";
        StringBuffer matID = new StringBuffer();
        ArrayList<String> dimensID = new ArrayList<>();
        dimensID.addAll(appDatabase.plumbingDAO().getAllDimensID());

        matID.append("PL");
        matID.append(inputMaterialName.getText().toString().substring(0, 1));
        while ( dimensID.contains(dimenid) && dimensID.size() > 0) {
            dimenid = String.valueOf(rand.nextInt(100));
            plumbing.setDimenID(dimenid);
        }//while;
        matID.append(dimenid);
        matID.append(inputType.getText().toString().substring(0,1));

        Toast.makeText(this, matID.toString(), Toast.LENGTH_SHORT).show();

        return matID.toString();
    }//generateMatId;

    private class MyTextWatcher implements TextWatcher {

        private View v;

        public MyTextWatcher(View v) {
            this.v = v;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch ( v.getId() ) {
                case R.id.input_mat_name_plumbing:
                    validateData();
                    break;
                case R.id.input_type_plumbing:
                    validateData();
                    break;
                case R.id.input_dimen_plumbing:
                    validatePlumbingDimens(charSequence.toString());
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

        private void validatePlumbingDimens(String dimen) {

            if ( !dimen.matches("[0-9]+['][0-9]+[\"]") ) {
                layoutInputDimen.setError("Please Enter a valid Dimension...");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }

        void validateData() {
            if ( !inputMaterialName.getText().toString().isEmpty() &&
                    !inputType.getText().toString().isEmpty() ) {
                btnAdd.setEnabled(true);
            } else {
                btnAdd.setEnabled(false);
            }//else
        }//validateData;
    }

    private class DimenTextWatcher implements TextWatcher {

        private View v;
        public DimenTextWatcher(View v){
            this.v = v;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }


        private void validateFittingsDimens(String dimen) {
            if ( !dimen.matches("[0-9]+[\"X\"][0-9]+") ) {
                layoutInputDimen.setError("Please enter a valid Dimension value");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }

        }

        private void validateElectricalsDimens(String dimen) {
            if ( !dimen.matches("[0-9]+[\"X\"][0-9]+") ) {
                layoutInputDimen.setError("Please enter a valid Dimension value");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }

    }//MyTextWatcher;

    @Override
    protected void onDestroy(){
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

}//AddPlumbingActivity;
