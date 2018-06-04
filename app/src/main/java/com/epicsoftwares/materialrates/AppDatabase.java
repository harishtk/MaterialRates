package com.epicsoftwares.materialrates;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Profile.class, Plumbing.class, Fittings.class, RateTable.class, Electricals.class, ShopTable.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProfileDAO profileDAO();
    public abstract PlumbingDAO plumbingDAO();
    public abstract FittingsDAO fittingsDAO();
    public abstract ElectricalsDAO electricalsDAO();
    public abstract RateTableDAO rateTableDAO();
    public abstract ShopTableDAO shopTableDAO();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDB(Context context) {

        if ( INSTANCE == null ) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app-database" )
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }//getDB;

    public static void destroyInstance() {
        INSTANCE = null;
    }//destroyInstance;
}//AppDatabase;

