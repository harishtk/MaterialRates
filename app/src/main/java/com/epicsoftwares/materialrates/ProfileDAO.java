package com.epicsoftwares.materialrates;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProfileDAO {

    @Insert(onConflict = REPLACE)
    void insertProfile(Profile... profiles);

    @Query("SELECT COUNT(*) FROM profiles")
    int getNoOfProfiles();

    @Query("SELECT * FROM profiles WHERE mobile_number LIKE :mob OR mobile_number = :mob")
    Profile getByMobileNumber(String mob);

    @Query("SELECT first_name FROM profiles")
    List<String> getAllFirstName();

    @Query("SELECT * FROM profiles WHERE first_name LIKE :firstName OR first_name = :firstName")
    Profile getByFirstName(String firstName);

    @Query("SELECT * FROM profiles")
    List<Profile> getAllProfiles();

    @Delete
    void deleteProfile(Profile profile);

}