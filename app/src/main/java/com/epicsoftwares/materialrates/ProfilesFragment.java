package com.epicsoftwares.materialrates;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProfilesFragment extends Fragment {

    private static final int REQUEST_CALL_PHONE = 1343;

    private AppCompatButton btnSrch;
    private AppCompatAutoCompleteTextView inputProfileNameMobile;
    private TextInputLayout layoutInputProfileNameMobile;
    private ListView listView;

    private ArrayAdapter<String> adapter;
    private ArrayList<Profile> profileArrayList;
    private List<String> suggestions;

    private AppDatabase appDatabase;

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profiles, container, false);
    }//onCreateView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mView = getView();

        appDatabase = AppDatabase.getDB(getActivity());

        profileArrayList = new ArrayList<>();

        suggestions = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, suggestions);

        btnSrch = getView().findViewById(R.id.srch_btn);

        inputProfileNameMobile = getView().findViewById(R.id.input_prof_name);
        layoutInputProfileNameMobile = getView().findViewById(R.id.layout_input_prof_name);
        listView = getView().findViewById(R.id.list_view);

        registerForContextMenu(listView);

        inputProfileNameMobile.setThreshold(1);
        inputProfileNameMobile.setAdapter(adapter);
        inputProfileNameMobile.addTextChangedListener(new MyTextWatcher(inputProfileNameMobile));

        FloatingActionButton fab = getView().findViewById(R.id.fab_profile);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openAddProfileActivity = new Intent(getActivity(), AddProfileActivity.class);
                startActivity(openAddProfileActivity);
            }
        });

        btnSrch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Profile prof;
                if ( inputProfileNameMobile.getText().toString().matches("[0-9]+") )
                    prof = appDatabase.profileDAO().getByMobileNumber(inputProfileNameMobile.getText().toString());
                else
                    prof = appDatabase.profileDAO().getByFirstName(inputProfileNameMobile.getText().toString());

                if ( prof != null ) {
                    profileArrayList.clear();
                    profileArrayList.add(prof);
                    listView.setAdapter(new CustomProfileViewAdapter(profileArrayList, getActivity()));
                } else {
                    final Snackbar snackbar = Snackbar.make(getView(), "Sorry! profile not found.", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }
            }
        });

    }//onViewCreated;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle(new StringBuilder().append(profileArrayList.get(info.position).getFirstName()).append(" ").append(profileArrayList.get(info.position).getLastName()).toString());
        menu.add(Menu.NONE, v.getId(), 0, "Call");
        menu.add(Menu.NONE, v.getId(), 0, "Delete");

    }//onCreateContextMenu;

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final Intent callMediatorIntent = new Intent(Intent.ACTION_CALL);

        callMediatorIntent.setData(Uri.parse("tel:" + profileArrayList.get(0).getMob()));

        AlertDialog.Builder builderCall = new AlertDialog.Builder(getActivity()).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(callMediatorIntent);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        }).setTitle("Call " + profileArrayList.get(0).getMob() + "?");

        AlertDialog.Builder builderDelete = new AlertDialog.Builder(getActivity()).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteProfile();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        }).setTitle("Delete Profile?").setMessage("This can't be undone!");

        Toast.makeText(getActivity(), "Item id: " + item.getItemId(), Toast.LENGTH_SHORT);

        if ( item.getTitle() == "Call" ) {

            if ( ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED ) {
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
            }
            AlertDialog dialog = builderCall.create();
            dialog.show();
        } else if ( item.getTitle() == "Delete") {
            AlertDialog dialog = builderDelete.create();
            dialog.show();
        }

        return true;
    }//onContextItemSelected;

    private void deleteProfile() {
        final Profile prof = profileArrayList.get(0);
        appDatabase.profileDAO().deleteProfile(prof);
        profileArrayList.remove(0);
        profileArrayList.notify();
        final Snackbar snackbar = Snackbar.make(mView, "Profile Deleted " + prof.getFirstName(), Snackbar.LENGTH_SHORT);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appDatabase.profileDAO().insertProfile(prof);
                        Toast.makeText(getActivity(), "Profile Added!", Toast.LENGTH_SHORT).show();
                        snackbar.dismiss();
                    }
                });

        if ( appDatabase.profileDAO().getByMobileNumber(prof.getMob()) == null )
            snackbar.show();
        else
            Toast.makeText(getActivity(), "Profile not Deleted!", Toast.LENGTH_SHORT).show();

    }//deleteProfile;

    private class MyTextWatcher implements TextWatcher {
        private View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if ( charSequence.toString().matches("[^0-9]+") ) {
                suggestions.addAll(appDatabase.profileDAO().getAllFirstName());
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}//ProfilesFragment;