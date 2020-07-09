package com.example.clothingwarehousemanagement.Data;

import androidx.annotation.NonNull;

public class Shelf {
    private String shelfID; //货架id
    private int storageLocation; //货位
    private String clothingID; //所存储的衣服
    private int quantity; //所存的衣服的数量

    public Shelf(){
        this.shelfID = "";
        this.storageLocation = 0;
        this.clothingID = "";
        this.quantity = 0;
    }

    public Shelf(String shelfID,int storageLocation) {
        this.shelfID = shelfID;
        this.storageLocation = storageLocation;
        this.clothingID = "";
        this.quantity = 0;
    }

    public String getShelfID() {
        return shelfID;
    }

    public void setShelfID(String shelfID) {
        this.shelfID = shelfID;
    }

    public int getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(int storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getClothingID() {
        return clothingID;
    }

    public void setClothingID(String clothingID) {
        this.clothingID = clothingID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Shelf(String shelfID, int storageLocation, String clothing, int quantity) {
        this.shelfID = shelfID;
        this.storageLocation = storageLocation;
        this.clothingID = clothing;
        this.quantity = quantity;
    }

    public void setLocation(String shelfID,int storageLocation) {
        this.shelfID = shelfID;
        this.storageLocation = storageLocation;
    }

    @NonNull
    @Override
    public String toString() {
        return shelfID+"-"+storageLocation+"-"+clothingID+"-"+quantity;
    }
}
