package com.epicsoftwares.materialrates;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "shop_table")
public class ShopTable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "shop_id")
    private String shopID;

    @NonNull
    @ColumnInfo(name = "shop_name")
    private String shopName;

    @NonNull
    @ColumnInfo(name = "shop_loc")
    private String shopLocation;

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public String getShopID() {
        return shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopLocation() {
        return shopLocation;
    }

}