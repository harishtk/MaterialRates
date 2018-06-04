package com.epicsoftwares.materialrates;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FittingsDAO {

    @Insert(onConflict = REPLACE)
    void insertFittings(Fittings... fittings);

    @Query("SELECT mat_name FROM fittings")
    List<String> getAllMatNames();

    @Query("SELECT type FROM fittings")
    List<String> getAllTypes();

    @Query("SELECT dimen FROM fittings")
    List<String> getAllDimens();

    @Query("SELECT dimen_id FROM fittings")
    List<String> getAllDimensID();

    @Query("SELECT * FROM fittings WHERE mat_id LIKE :materialId")
    Fittings getFittings(String materialId);

    @Query("SELECT COUNT(*) FROM fittings")
    int getNoOfFittingsMaterials();

    @Query("SELECT dimen_id FROM fittings WHERE dimen LIKE :dimen")
    String getDimensID(String dimen);

}