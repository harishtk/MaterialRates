package com.epicsoftwares.materialrates;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ElectricalsDAO {

    @Insert(onConflict = REPLACE)
    void insertElectricals(Electricals... electricals);

    @Query("SELECT mat_name FROM electricals")
    List<String> getAllMatNames();

    @Query("SELECT type FROM electricals")
    List<String> getAllTypes();

    @Query("SELECT dimen FROM electricals")
    List<String> getAllDimens();

    @Query("SELECT dimen_id FROM electricals")
    List<String> getAllDimensID();

    @Query("SELECT * FROM electricals WHERE mat_id LIKE :materialId")
    Electricals getElectricals(String materialId);

    @Query("SELECT COUNT(*) FROM electricals")
    int getNoOfElectricalssMaterials();

    @Query("SELECT dimen_id FROM electricals WHERE dimen_id LIKE :dimen")
    String getDimensID(String dimen);

}