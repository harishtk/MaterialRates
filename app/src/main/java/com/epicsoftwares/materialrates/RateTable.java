package com.epicsoftwares.materialrates;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

@Entity(tableName = "rate_table")
public class RateTable{

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "mat_id")
    private String materialID;

    @Nullable
    @ColumnInfo(name = "shop_1")
    private String shop1;

    @Nullable
    @ColumnInfo(name = "shop_2")
    private String shop2;

    @Nullable
    @ColumnInfo(name = "shop_3")
    private String shop3;

    @Nullable
    @ColumnInfo(name = "shop_4")
    private String shop4;

    @Nullable
    @ColumnInfo(name = "shop_5")
    private String shop5;

    @Nullable
    @ColumnInfo(name = "shop_6")
    private String shop6;

    @Nullable
    @ColumnInfo(name = "shop_7")
    private String shop7;

    @Nullable
    @ColumnInfo(name = "shop_8")
    private String shop8;

    @Nullable
    @ColumnInfo(name = "shop_9")
    private String shop9;

    @Nullable
    @ColumnInfo(name = "shop_10")
    private String shop10;

    @Nullable
    @ColumnInfo(name = "shop_11")
    private String shop11;

    @Nullable
    @ColumnInfo(name = "shop_12")
    private String shop12;

    @Nullable
    @ColumnInfo(name = "shop_13")
    private String shop13;

    @Nullable
    @ColumnInfo(name = "shop_14")
    private String shop14;

    @Nullable
    @ColumnInfo(name = "shop_15")
    private String shop15;

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public String getMaterialID() {
        return materialID;
    }


    @Nullable
    public String getShop9() {
        return shop9;
    }

    public void setShop9(@Nullable String shop9) {
        this.shop9 = shop9;
    }

    @Nullable
    public String getShop1() {
        return shop1;
    }

    public void setShop1(@Nullable String shop1) {
        this.shop1 = shop1;
    }

    @Nullable
    public String getShop2() {
        return shop2;
    }

    public void setShop2(@Nullable String shop2) {
        this.shop2 = shop2;
    }

    @Nullable
    public String getShop3() {
        return shop3;
    }

    public void setShop3(@Nullable String shop3) {
        this.shop3 = shop3;
    }

    @Nullable
    public String getShop4() {
        return shop4;
    }

    public void setShop4(@Nullable String shop4) {
        this.shop4 = shop4;
    }

    @Nullable
    public String getShop5() {
        return shop5;
    }

    public void setShop5(@Nullable String shop5) {
        this.shop5 = shop5;
    }

    @Nullable
    public String getShop6() {
        return shop6;
    }

    public void setShop6(@Nullable String shop6) {
        this.shop6 = shop6;
    }

    @Nullable
    public String getShop7() {
        return shop7;
    }

    public void setShop7(@Nullable String shop7) {
        this.shop7 = shop7;
    }

    @Nullable
    public String getShop8() {
        return shop8;
    }

    public void setShop8(@Nullable String shop8) {
        this.shop8 = shop8;
    }

    @Nullable
    public String getShop10() {
        return shop10;
    }

    public void setShop10(@Nullable String shop10) {
        this.shop10 = shop10;
    }

    @Nullable
    public String getShop11() {
        return shop11;
    }

    public void setShop11(@Nullable String shop11) {
        this.shop11 = shop11;
    }

    @Nullable
    public String getShop12() {
        return shop12;
    }

    public void setShop12(@Nullable String shop12) {
        this.shop12 = shop12;
    }

    @Nullable
    public String getShop13() {
        return shop13;
    }

    public void setShop13(@Nullable String shop13) {
        this.shop13 = shop13;
    }

    @Nullable
    public String getShop14() {
        return shop14;
    }

    public void setShop14(@Nullable String shop14) {
        this.shop14 = shop14;
    }

    @Nullable
    public String getShop15() {
        return shop15;
    }

    public void setShop15(@Nullable String shop15) {
        this.shop15 = shop15;
    }
}