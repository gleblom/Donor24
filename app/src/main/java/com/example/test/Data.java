package com.example.test;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


//Класс для сохранения данных из представления dataview
public class Data implements Parcelable {
    int id;
    ItemModel status;
    ItemModel component;
    String donationData;
    String conserveData;
    ItemModel group;
    ItemModel rh;
    String uuid;
    public Data(int id, ItemModel status, ItemModel component,
                String donationData, String conserveData, ItemModel group, ItemModel rh,
                String uuid){
        this.id = id;
        this.status = status;
        this.component = component;
        this.donationData = donationData;
        this.conserveData = conserveData;
        this.group = group;
        this.rh = rh;
        this.uuid = uuid;
    }

    protected Data(Parcel in) {
        id = in.readInt();
        donationData = in.readString();
        conserveData = in.readString();
        uuid = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setRh(ItemModel rh) {
        this.rh = rh;
    }

    public void setStatus(ItemModel status) {
        this.status = status;
    }

    public void setDonationData(String donationData) {
        this.donationData = donationData;
    }

    public void setGroup(ItemModel group) {
        this.group = group;
    }

    public void setComponent(ItemModel component) {
        this.component = component;
    }

    public ItemModel getGroup() {
        return group;
    }

    public String getDonationData() {
        return donationData;
    }

    public ItemModel getComponent() {
        return component;
    }

    public ItemModel getRh() {
        return rh;
    }

    public String getConserveData() {
        return conserveData;
    }

    public void setConserveData(String conserveData) {
        this.conserveData = conserveData;
    }

    public ItemModel getStatus() {
        return status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(donationData);
        dest.writeString(conserveData);
        dest.writeString(uuid);
    }
}
