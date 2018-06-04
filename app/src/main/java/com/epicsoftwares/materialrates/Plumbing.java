package com.epicsoftwares.materialrates;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Random;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "plumbing")
public class Plumbing {

    @ColumnInfo(name = "mat_name")
    private String materialName;

    @ColumnInfo(name = "dimen")
    private String dimen;

    @ColumnInfo(name = "dimen_id")
    private String dimenID;

    @ColumnInfo(name = "type")
    private String type;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "mat_id")
    private String materialID;

    public void setMaterialName(String materailName) {
        this.materialName = materailName;
    }

    public void setDimen(String dimen) {
        this.dimen = dimen;
    }

    public void setDimenID(String dimenID) {
        this.dimenID = dimenID;
    }

    public String getDimenID() {
        return dimenID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMaterialID(String materialID){
        this.materialID = materialID;
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
