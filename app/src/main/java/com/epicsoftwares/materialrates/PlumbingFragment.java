package com.epicsoftwares.materialrates;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlumbingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_plumbing, container, false);
    }//onCreateView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }//onCreate;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        FloatingActionButton fab = getView().findViewById(R.id.fab_plumbing_fragment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openAddPlumbingActivity = new Intent(getActivity(), AddPlumbingActivity.class);
                startActivity(openAddPlumbingActivity);
            }
        });
    }
}