package com.epicsoftwares.materialrates;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomProfileViewAdapter extends BaseAdapter {

    private ArrayList<Profile> data;
    private Context mContext;

    public CustomProfileViewAdapter(ArrayList<Profile> data, Context context) {
        this.data = data;
        mContext = context;
    }//constructor;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if ( v == null ) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.profile_list_view, null);
        }
        ImageView imageView = v.findViewById(R.id.img);
        TextView name = v.findViewById(R.id.txt_name);
        TextView mobile = v.findViewById(R.id.txt_mobile);
        TextView city = v.findViewById(R.id.txt_city);

        Profile profile = data.get(i);
        if ( profile != null ) {

            imageView.setImageBitmap(BitmapFactory.decodeByteArray(profile.getProfPic(), 0, profile.getProfPic().length));
            name.setText(new StringBuilder(profile.getFirstName()).append(" ").append(profile.getLastName()));
            mobile.setText(profile.getMob());
            city.setText(profile.getCity());
        }

        return v;
    }
}