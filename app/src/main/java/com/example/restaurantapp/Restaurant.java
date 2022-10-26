package com.example.restaurantapp;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class Restaurant {
    private String name;
    @SerializedName(value = "place_id")
    private String id;
    @SerializedName(value = "price_level")
    private int priceLevel;
    private double rating;

    private String[] types;
    @SerializedName(value = "vicinity")
    private String location;
//    private double latitude, longitude;
//    private List<Review> reviews;


    @Override
    public String toString() {
        return  name + System.lineSeparator() +
                "id=" + id +  System.lineSeparator() +
                "priceLevel=" + priceLevel + System.lineSeparator() +
                "rating=" + rating + System.lineSeparator() +
                "types=" + Arrays.toString(types) + System.lineSeparator() +
                "location=" + location + System.lineSeparator() +
                "**********************************************" + System.lineSeparator();
    }

}
