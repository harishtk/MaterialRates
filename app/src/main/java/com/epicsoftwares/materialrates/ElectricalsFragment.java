package com.epicsoftwares.materialrates;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ElectricalsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        return inflater.inflate(R.layout.fragment_electricals, container, false);
    }//onCreateView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        FloatingActionButton fab = getView().findViewById(R.id.fab_electricals_fragment);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent openAddElectricalsActivity = new Intent(getActivity(), AddElectricalsActivity.class);
                startActivity(openAddElectricalsActivity);
            }
        });
    }
}