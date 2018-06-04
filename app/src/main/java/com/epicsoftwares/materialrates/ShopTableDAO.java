package com.epicsoftwares.materialrates;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ShopTableDAO {

    @Insert(onConflict = REPLACE)
    void insertShop(ShopTable... shopTables);

    @Query("SELECT * FROM shop_table WHERE shop_id LIKE :shopID")
    ShopTable getShop(String shopID);

}