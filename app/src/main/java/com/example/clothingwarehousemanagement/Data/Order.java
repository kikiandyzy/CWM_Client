package com.example.clothingwarehousemanagement.Data;

import androidx.annotation.NonNull;

import java.util.Map;

public class Order {
    private String ID;
    private String date;
    private Map<String,Integer> order;

    public Order(String ID,String date,Map<String,Integer> order){
        this.ID = ID;
        this.date = date;
        this.order = order;
    }

    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the order
     */
    public Map<String,Integer> getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Map<String,Integer> order) {
        this.order = order;
    }

    @NonNull
    @Override
    public String toString() {
        return ID+date;
    }
}

