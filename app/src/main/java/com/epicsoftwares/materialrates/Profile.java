package com.epicsoftwares.materialrates;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "profiles")
public class Profile {

    @NonNull
    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "mobile_number")
    private String mob;

    @Nullable
    @ColumnInfo(name = "prof_pic")
    private byte[] profPic;

    @Nullable
    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "city")
    private String city;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMob() {
        return mob;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getProfPic() {
        return profPic;
    }

    public String getCity() {
        return city;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfPic(byte[] profPic) {
        this.profPic = profPic;
    }

    public void setCity(String city) {
        this.city = city;
    }

}