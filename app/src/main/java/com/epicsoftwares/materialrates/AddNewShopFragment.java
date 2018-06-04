package com.epicsoftwares.materialrates;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

public class AddNewShopFragment extends Fragment {

    public static final String SHOP_COUNT_PREFERENCES = "MyPrefs";

    private AppCompatButton btnAdd;
    private TextInputLayout layoutInputShopName;
    private AppCompatEditText inputShopName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_shop, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHOP_COUNT_PREFERENCES, Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("shopcount", 0);
        editor.apply();

        layoutInputShopName = getView().findViewById(R.id.layout_input_shop_name);
        inputShopName = getView().findViewById(R.id.input_shop_name);
        btnAdd = getView().findViewById(R.id.btn_add_add_new_shop);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int shopcount = sharedPreferences.getInt("shopcount", 0);
                String shopKey = new StringBuilder().append("shop_").append(shopcount).toString();
                editor.putString(shopKey, inputShopName.getText().toString());
                editor.putInt("shopcount", shopcount + 1 );
                editor.commit();
                Toast.makeText(getActivity(), "Shop added.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}