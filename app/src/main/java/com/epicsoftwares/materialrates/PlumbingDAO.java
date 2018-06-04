package com.epicsoftwares.materialrates;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PlumbingDAO{

    @Insert(onConflict = REPLACE)
    void insertPlumbing(Plumbing... plumbing);

    @Query("SELECT mat_name FROM plumbing")
    List<String> getAllMatNames();

    @Query("SELECT type FROM plumbing")
    List<String> getAllTypes();

    @Query("SELECT dimen FROM plumbing")
    List<String> getAllDimens();

    @Query("SELECT dimen_id FROM plumbing")
    List<String> getAllDimensID();

    @Query("SELECT * FROM plumbing WHERE mat_id LIKE :materialId")
    Plumbing getPlumbing(String materialId);

    @Query("SELECT COUNT(*) FROM plumbing")
    int getNoOfPlumbingMaterials();

    @Query("SELECT dimen_id FROM plumbing WHERE dimen LIKE :dimens")
    String getDimensID(String dimens);

}