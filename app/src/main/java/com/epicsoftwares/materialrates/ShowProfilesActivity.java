package com.epicsoftwares.materialrates;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowProfilesActivity extends AppCompatActivity {

    private ListView showProfilesListView;

    private ArrayList<Profile> profileArrayAdapter;

    private AppDatabase appDatabase;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profiles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appDatabase = AppDatabase.getDB(this);

        showProfilesListView = findViewById(R.id.show_profiles_list_view);
        coordinatorLayout = findViewById(R.id.coordinator);

        profileArrayAdapter = new ArrayList<>();

        profileArrayAdapter.addAll(appDatabase.profileDAO().getAllProfiles());

        final  Snackbar snackbar = Snackbar.make(coordinatorLayout, "Oops! Something happned, Please try again.", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        if ( !profileArrayAdapter.isEmpty() )
            showProfilesListView.setAdapter(new CustomProfileViewAdapter(profileArrayAdapter, this));
        else
            snackbar.show();
    }//onCreate;

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }//onDestroy;
}//ShowProfilesActivity;
