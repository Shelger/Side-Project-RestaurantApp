package com.example.restaurantapp;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RestaurantData {
    private final double curLatitude;
    private final double curLongitude;
    private final List<Restaurant> list;
    private final long distance;

    public RestaurantData(double lat, double lon, long distance) {
        this.curLatitude = lat;
        this.curLongitude = lon;
        this.distance = distance;
        list = new ArrayList<Restaurant>();
        addRestaurants();
    }

    public void addRestaurants () {
        String host = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?keyword=restaurant" +
                "&location=" + curLatitude + "," + curLongitude +
                "&radius=" + distance +
                "&key=AIzaSyAbkZwMglVk7o-i1N93RhJy9abVbHHs_PE";

//        String charset = "UTF-8";
        try {
            HttpResponse<JsonNode> response = Unirest.get(host)
                    .asJson();
//        System.out.println(response.getStatus());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonObject jsonObject = (JsonObject) jp.parse(response.getBody().toString());
            JsonArray arr = jsonObject.getAsJsonArray("results");
            for (JsonElement elem : arr) {
                list.add(gson.fromJson(elem, Restaurant.class));
            }
        } catch (UnirestException e) {
            System.out.println("UnirestException " + e);
        }
//        String prettyJsonString = gson.toJson(je);
//        System.out.println(prettyJsonString);
    }

    public void printRestaurants() {
        for(Restaurant r: list) System.out.println(r);
    }

    public static void main(String[] args) {
        RestaurantData rd = new RestaurantData(32.987145999999996, -96.92448, 500);
        rd.printRestaurants();
    }
}
