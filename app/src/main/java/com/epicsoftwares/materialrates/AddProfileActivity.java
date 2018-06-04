package com.epicsoftwares.materialrates;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class AddProfileActivity extends AppCompatActivity {

     private static final int REQUEST_IMAGE_CAPTURE = 1231;

     private AppDatabase appDatabase;

    private AppCompatEditText firstName;
    private AppCompatEditText lastName;
    private AppCompatEditText mobileNo;
    private AppCompatEditText email;
    private AppCompatEditText city;
    private ImageView profImg;

    private TextInputLayout layoutFirstName;
    private TextInputLayout layoutLastName;
    private TextInputLayout layoutMobileNo;
    private TextInputLayout layoutEmail;
    private TextInputLayout layoutCity;

    private AppCompatButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        getSupportActionBar().setTitle("Add a New Mediator Profile");

        appDatabase = AppDatabase.getDB(this);

        profImg = findViewById(R.id.prof_img);
        firstName = findViewById(R.id.input_first_name);
        lastName = findViewById(R.id.input_last_name);
        mobileNo = findViewById(R.id.input_mobile);
        email = findViewById(R.id.input_email);
        city = findViewById(R.id.input_city);

        layoutFirstName = findViewById(R.id.layout_input_first_name);
        layoutLastName = findViewById(R.id.layout_input_last_name);
        layoutMobileNo = findViewById(R.id.layout_input_mobile);
        layoutEmail = findViewById(R.id.layout_input_email);
        layoutCity = findViewById(R.id.layout_input_city);

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setEnabled(false);

        firstName.addTextChangedListener(new MyTextWatcher(firstName));
        lastName.addTextChangedListener(new MyTextWatcher(lastName));
        mobileNo.addTextChangedListener(new MyTextWatcher(mobileNo));
        email.addTextChangedListener(new MyTextWatcher(email));
        city.addTextChangedListener(new MyTextWatcher(city));

        profImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setProfilePicture();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Profile profile = new Profile();
                if ( !firstName.getText().toString().isEmpty() ) profile.setFirstName(firstName.getText().toString());
                if ( !lastName.getText().toString().isEmpty() ) profile.setLastName(lastName.getText().toString());
                if ( !mobileNo.getText().toString().isEmpty() ) profile.setMob(mobileNo.getText().toString());
                if ( !email.getText().toString().isEmpty() ) profile.setEmail(email.getText().toString());
                if ( !city.getText().toString().isEmpty() ) profile.setCity(city.getText().toString());
                Bitmap byte_image = ((BitmapDrawable) profImg.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                byte_image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                profile.setProfPic(stream.toByteArray());

                appDatabase.profileDAO().insertProfile(profile);
                Toast.makeText(getApplicationContext(), "Profile Added!", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "No, of users: " + appDatabase.profileDAO().getNoOfProfiles(), Toast.LENGTH_SHORT).show();
            }

        });

    }//onCreate;

    private void setProfilePicture() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Picture?");
        builder.setMessage("Do you want to change the picture?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if ( takePictureIntent.resolveActivity(getPackageManager()) != null )
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED )
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);

        AlertDialog dialog = builder.create();
        dialog.show();
    }//setProfilePicture;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
            profImg.setImageBitmap((Bitmap) data.getExtras().get("data"));
    }//onActivityResult;

    private class MyTextWatcher implements TextWatcher {

        private View view;

        public MyTextWatcher(View v){
            this.view = v;
        }//constructor;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }//beforeTextChanged;

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            switch ( view.getId() ) {
                case R.id.input_first_name: validateFirstName();
                                            checkDataValidity();
                    break;
                case R.id.input_last_name: validateLastName();
                                            checkDataValidity();
                    break;
                case R.id.input_email: validateEmail();
                                        checkDataValidity();
                    break;
                case R.id.input_city: validateCity();
                                        checkDataValidity();
                    break;
                case R.id.input_mobile: validateMobile();
                                        checkDataValidity();
                    break;
            }//switch;
        }//onTextChanged;

        @Override
        public void afterTextChanged(Editable editable) {

        }//afterTextChanged;

        private boolean validateFirstName() {
            String first_name = firstName.getText().toString().trim();
            if ( first_name.isEmpty() ) {
                layoutFirstName.setError("This field cannot be empty!");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return false;
            } else if ( !first_name.matches("[^0-9]+") ) {
                layoutFirstName.setError("Should not contain numbers!");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return false;
            } else layoutFirstName.setErrorEnabled(false);
            return true;
        }//validataFirstName;

        private boolean validateLastName() {
            String last_name = lastName.getText().toString().trim();
            if ( !last_name.matches("[^0-9]+") ) {
                layoutLastName.setError("Should not conain numbers!");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return false;
            } else
                layoutLastName.setErrorEnabled(false);
            return true;
        }//validateLastName;

        private boolean validateMobile() {
            String mob = mobileNo.getText().toString().trim();
            if ( mob.isEmpty() ) {
                layoutMobileNo.setError("This field cannot be empty!");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return false;
            } else if  ( !mob.matches("[^a-zA-Z]+") ) {
                layoutMobileNo.setError("Enter a valid number");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return false;
            } else layoutMobileNo.setErrorEnabled(false);
            return true;
        }//validateMobile;

        private boolean validateEmail() {
            String e_mail= email.getText().toString().trim();
            if ( e_mail.isEmpty() || !validEmail(e_mail) ) {
                layoutEmail.setError("Enter a valid  E-Mail!");
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return false;
            } else layoutEmail.setErrorEnabled(false);
            return true;
        }//validateEmail;

        private boolean validEmail(String e_mail) {
            return !TextUtils.isEmpty(e_mail) && Patterns.EMAIL_ADDRESS.matcher(e_mail).matches();
        }//validEmail;

        private boolean validateCity() {
            return true;
        }//validateCity;

        private void checkDataValidity() {
            if ( validateFirstName() && validateMobile() )
                btnAdd.setEnabled(true);
            else btnAdd.setEnabled(false);
        }//checkDataValidity;
    }//MyTextWatcher;

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }//onDestroy;

}//AddProfileActivity;
