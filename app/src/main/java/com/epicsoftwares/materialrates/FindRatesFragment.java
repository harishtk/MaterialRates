package com.epicsoftwares.materialrates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FindRatesFragment extends Fragment {

    private String category;
    private String dimen;
    private String type;
    private String matID;

    private Spinner spinnerCategory;
    private Spinner spinnerDimen;
    private Spinner spinnerType;
    private AppCompatButton btnFind;

    private ArrayAdapter<String> spinnerDimenAdapter;
    private ArrayAdapter<String> spinnerTypeAdapter;

    private ArrayList<String> spinnerDimenItems;
    private ArrayList<String> spinnerTypeItems;

    private AppDatabase appDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup conatiner, Bundle savedInstaceState) {
        return inflater.inflate(R.layout.fragment_find_rates, conatiner, false);
    }//onCreateView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        appDatabase = AppDatabase.getDB(getActivity());

        spinnerCategory = getView().findViewById(R.id.spinner_select_category_find_rates);
        spinnerDimen = getView().findViewById(R.id.spinner_select_dimen_find_rates);
        spinnerType = getView().findViewById(R.id.spinner_select_type_find_rates);
        btnFind = getView().findViewById(R.id.btn_find_find_rates);

        spinnerDimen.setEnabled(false);
        spinnerType.setEnabled(false);
        btnFind.setEnabled(false);

        spinnerDimenAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerDimenItems);
        spinnerTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerTypeItems);

        spinnerDimenAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerTypeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerDimen.setAdapter(spinnerDimenAdapter);
        spinnerType.setAdapter(spinnerTypeAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapterView.getItemAtPosition(i).toString();
                spinnerDimen.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinnerDimen.setEnabled(false);
                spinnerType.setEnabled(false);
            }
        });

        spinnerDimen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dimen = adapterView.getItemAtPosition(i).toString();
                spinnerType.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinnerDimen.setEnabled(false);
                spinnerType.setEnabled(false);
            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
                if ( spinnerCategory.isSelected() &&
                        spinnerDimen.isSelected() &&
                        spinnerType.isSelected() )
                    btnFind.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                matID = generateMatID();
                startIntent();
            }
        });

    }//onViewCreated;

    private String generateMatID() {
        StringBuffer materialID = null;

        if ( category.toUpperCase().equalsIgnoreCase("PLUMBING")) {
            materialID.append("PL");
        } else if ( category.toUpperCase().equalsIgnoreCase("FITTINGS")) {
            materialID.append("FI");

        } else if ( category.toUpperCase().equalsIgnoreCase("ELECTRICALS")) {
            materialID.append("EL");
        }

        materialID.append(category.substring(0,1).toUpperCase());
        if ( category.toUpperCase().equalsIgnoreCase("PLUMBING")) {
            materialID.append(appDatabase.plumbingDAO().getDimensID(dimen));
            Toast.makeText(getActivity(), "DimensID: " + appDatabase.plumbingDAO().getDimensID(dimen), Toast.LENGTH_SHORT).show();
        } else if ( category.toUpperCase().equalsIgnoreCase("FITTINGS")) {
            materialID.append(appDatabase.fittingsDAO().getDimensID(dimen));
        } else if ( category.toUpperCase().equalsIgnoreCase("ELECTRICALS")) {
            materialID.append(appDatabase.electricalsDAO().getDimensID(dimen));
        }

        materialID.append(type.substring(0, 1));

        return materialID.toString();
    }//generateMatID;

    private void startIntent() {
        Intent openRatesActivity = new Intent(getActivity(), RatesActivity.class);
        openRatesActivity.putExtra("matID", matID);
        startActivity(openRatesActivity);
    }//startIntent;

}//FindRatesFragment;