package restaurantInfo;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

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
    private double latitude, longitude;
//    private List<Review> reviews;


    public Restaurant(String name, String id, int priceLevel, double rating, String location, double latitude, double longitude) {
        this.name = name;
        this.id = id;
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return  name + System.lineSeparator() +
                "id=" + id +  System.lineSeparator() +
                "priceLevel=" + priceLevel + System.lineSeparator() +
                "rating=" + rating + System.lineSeparator() +
                "types=" + Arrays.toString(types) + System.lineSeparator() +
                "location=" + location + System.lineSeparator() +
                latitude + "," + longitude +
                "**********************************************" + System.lineSeparator();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public double getRating() {
        return rating;
    }

    public String[] getTypes() {
        return types;
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
