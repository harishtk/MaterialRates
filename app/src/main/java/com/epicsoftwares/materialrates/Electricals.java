package com.epicsoftwares.materialrates;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "electricals")
public class Electricals {

    @ColumnInfo(name = "mat_name")
    private String materialName;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "mat_id")
    private String materialID;

    @ColumnInfo(name = "dimen")
    private String dimen;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "dimen_id")
    private String dimenID;

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public void setDimen(String dimen) {
        this.dimen = dimen;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMaterialID(String materialID){
        this.materialID = materialID;
    }

    public void setDimenID(String dimenID) {
        this.dimenID = dimenID;
    }

    public String getDimenID() {
        return getDimenID();
    }

    public String getMaterialName(){
        return materialName;
    }

    public String getDimen(){
        return dimen;
    }

    public String getType() {
        return type;
    }

    public String getMaterialID() {
        return materialID;
    }

}